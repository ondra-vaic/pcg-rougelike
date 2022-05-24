package cz.cuni.gamedev.nail123.roguelike.world.worlds

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.blocks.Floor
import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.objects.Door
import cz.cuni.gamedev.nail123.roguelike.entities.objects.Stairs
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding.eightDirectional
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding.floodFill
import cz.cuni.gamedev.nail123.roguelike.world.Area
import cz.cuni.gamedev.nail123.roguelike.world.builders.wavefunctioncollapse.WFCAreaBuilder
import org.hexworks.zircon.api.data.Position3D
import kotlin.math.abs
import kotlin.math.sign
import kotlin.random.Random

class WaveFunctionCollapsedWorld: DungeonWorld() {
    override fun buildLevel(floor: Int): Area {
        val area = WFCAreaBuilder(GameConfig.AREA_SIZE).create()

//        area.addEntity(FogOfWar(), Position3D.unknown())

        area.player.ResetHealth()

        // Add stairs up
        if (floor > 0) area.addEntity(Stairs(false), area.player.position)

        // removes areas of walls which are too small
        removeAreas<Wall>(area, 10) { Floor() }

        // removes areas of floor which are too small
        removeAreas<Floor>(area, 10) { Wall() }

        // connects rooms
        connectRooms(area)

        // remove cycles
        removeCycles(area)

        // remove small areas of wall again
        removeAreas<Wall>(area, 10) { Floor() }

        // remove walls which are surrounded by 6 and more floors
        removeSurroundedWall(area)

        // remove doors which are not in a corridor
        removeLonelyDoors(area)

        area.addAtEmptyPosition(
            area.player,
            Position3D.create(0, 0, 0),
            GameConfig.VISIBLE_SIZE
        )

        // This fixed weird bug with walls rendering I don't know why
        for ((k, v) in area.blocks){
            if(v is Wall){
                area.blocks[k] = Wall()
            }
        }
        if (floor < GameConfig.DUNGEON_FLOORS){
            val floorPosition = area.getFirstEmptyPosition()
            val mapFill = floodFill(floorPosition, area)
            val maxDistance = mapFill.values.maxOrNull()!!
            val staircasePosition = mapFill.filter { it.value > maxDistance / 2 }.keys.first()
            area.addEntity(Stairs(), staircasePosition)
        }
        placeEnemies(player.position, area, floor)

        return area.build()
    }

    private fun placeEnemies(playerPosition: Position3D, area: WFCAreaBuilder, floor: Int){

        val rng = Random.Default

        val minDistanceToPlayer = 14
        val mapFill = floodFill(playerPosition, area)
        val corridors = findCorridors(area)
        var possiblePositions = mapFill.filter { it.value > minDistanceToPlayer }.keys
        for (corridor in corridors){
            possiblePositions = possiblePositions - corridor
        }

        val level = floor / GameConfig.DUNGEON_LEVELS + 1
        val levelProgress = (floor % GameConfig.DUNGEON_LEVELS) / GameConfig.DUNGEON_LEVELS.toFloat()
        val numEnemies = (rng.nextInt(5, 10) + levelProgress * rng.nextInt(6, 15)).toInt()

        repeat(numEnemies){

        }
    }

    private fun removeCycles(area: WFCAreaBuilder){
        val corridors = findCorridors(area)

        val r = Random(0)

        for(corridor in corridors){

            val connectedArea = floodFill(corridor.first(), area).keys
            val connectedAreasWithoutCorridor = connectedArea - corridor
            fillPositions(area, corridor) { Wall() }

            if(connectedAreasWithoutCorridor.isEmpty()) continue

            val connectedAreaAfterRemove = floodFill(connectedAreasWithoutCorridor.first(), area)

            if(connectedAreaAfterRemove.size != connectedArea.size - corridor.size || r.nextInt(0, 2) == 0){
                fillPositions(area, corridor) { Floor() }
            }
        }
    }

    private fun fillPositions(area: WFCAreaBuilder, positions: Set<Position3D>, blockType : () -> GameBlock){
        for(position in positions){
            area.blocks[position] = blockType()
        }
    }

    private fun findCorridors(area: WFCAreaBuilder): HashSet<Set<Position3D>> {
        val corridors = HashSet<Set<Position3D>>()

        for ((position, _) in area.blocks){
            val corridor = findCorridor(area, position)
            if(corridor != null){
                corridors.add(corridor)
            }
        }

        return corridors;
    }

    private fun hasDiagonalNeighbors(area: WFCAreaBuilder, position: Position3D) : Boolean{
        if( hasNeighborOnOffset(area, position, 1, 1) ||
            hasNeighborOnOffset(area, position, -1, -1) ||
            hasNeighborOnOffset(area, position, 1, -1) ||
            hasNeighborOnOffset(area, position, -1, 1)) return true

        return false
    }

    private fun hasVerticalNeighbors(area: WFCAreaBuilder, position: Position3D) : Boolean{
        if( hasNeighborOnOffset(area, position, 0, 1) ||
            hasNeighborOnOffset(area, position, 0, -1)) return true

        return false
    }

    private fun hasHorizontalNeighbors(area: WFCAreaBuilder, position: Position3D) : Boolean{
        if( hasNeighborOnOffset(area, position, 1, 0) ||
            hasNeighborOnOffset(area, position, -1, 0)) return true

        return false
    }

    private fun hasNeighborOnOffset(area: WFCAreaBuilder, position: Position3D, x: Int, y: Int) : Boolean{
        return area.blocks[Position3D.create(position.x + x, position.y + y, 0)] is Floor
    }

    private fun findCorridor(area: WFCAreaBuilder, position: Position3D) : Set<Position3D>? {

        if(hasDiagonalNeighbors(area, position)) return null

        if(hasHorizontalNeighbors(area, position) && !hasVerticalNeighbors(area, position)){

            val rightPositions = getCorridorPositions(area, position, 1, 0)
            val leftPositions = getCorridorPositions(area, position, -1, 0)

            return rightPositions.union(leftPositions)
        } else if(hasVerticalNeighbors(area, position) && !hasHorizontalNeighbors(area, position)){

            val upPositions = getCorridorPositions(area, position, 0, 1)
            val downPositions = getCorridorPositions(area, position, 0, -1)

            return upPositions.union(downPositions)
        }

        return null
    }

    private fun getCorridorPositions(area: WFCAreaBuilder, startPosition: Position3D, xOffset: Int, yOffset: Int): HashSet<Position3D> {
        val positions = HashSet<Position3D>()
        positions.add(startPosition)

        var currentPosition = startPosition.copy()
        while(hasNeighborOnOffset(area, currentPosition, xOffset, yOffset) && !hasDiagonalNeighbors(area, currentPosition)){
            currentPosition = Position3D.create(currentPosition.x + xOffset, currentPosition.y + yOffset, 0)
            positions.add(currentPosition)
        }

        if(area[currentPosition] is Floor){
            positions.add(currentPosition)
        }

        return positions;
    }

    private fun removeLonelyDoors(area: WFCAreaBuilder) {
        for ((k, v) in area.blocks){
            if(k.x == Int.MAX_VALUE && k.y == Int.MAX_VALUE && k.z == Int.MAX_VALUE)
                continue

            var door: GameEntity? = null
            for (e in v.entities){
                if(e is Door){
                    door = e
                }
            }

            if(door != null){

                val neighborRight = area.blocks[Position3D.create(k.x + 1, k.y, 0)]
                val neighborLeft = area.blocks[Position3D.create(k.x - 1, k.y, 0)]

                val neighborUp = area.blocks[Position3D.create(k.x, k.y + 1, 0)]
                val neighborDown = area.blocks[Position3D.create(k.x, k.y - 1, 0)]

                val canHaveDoor = (
                    (neighborRight is Wall && neighborLeft is Wall && neighborUp is Floor && neighborDown is Floor) ||
                    (neighborRight is Floor && neighborLeft is Floor && neighborUp is Wall && neighborDown is Wall)
                )

                if(!canHaveDoor){
                    v.entities.remove(door)
                }
            }
        }
    }

    private fun removeSurroundedWall(area: WFCAreaBuilder) {
        var erasing = true
        while(erasing){
            erasing = false
            for ((k, v) in area.blocks){
                if(k.x == Int.MAX_VALUE && k.y == Int.MAX_VALUE && k.z == Int.MAX_VALUE)
                    continue

                if(v is Wall){
                    var numFloorNeighbors = 0
                    for(x in -1..1){
                        for(y in -1..1){
                            val neighbor = area.blocks[Position3D.create(k.x + x, k.y + y, 0)]
                            if(neighbor is Floor){
                                numFloorNeighbors++
                            }
                        }
                    }
                    if(numFloorNeighbors >= 6){
                        area.blocks[k] = Floor()
                        erasing = true
                    }
                }
            }
        }
    }

    private inline fun <reified T>removeAreas(area: WFCAreaBuilder, maxAreaSize: Int, blockType : () -> GameBlock){

        val filter = { block: GameBlock -> block !is T }

        for ((k, v) in area.blocks){
            if(k.x == Int.MAX_VALUE && k.y == Int.MAX_VALUE && k.z == Int.MAX_VALUE)
                continue

            if(v is Wall){
                val floodFill = floodFill(
                    k,
                    area,
                    eightDirectional,
                    filter
                )

                if(floodFill.count() < maxAreaSize){
                    for((position, _) in floodFill){
                        if(area.blocks[position] is T){
                            area.blocks[position] = blockType()
                        }
                    }
                }
            }
        }
    }

    private fun findRooms(area: WFCAreaBuilder) : Pair<HashMap<Int, HashSet<Position3D>>, HashMap<Position3D, Int>>{
        val connectedAreas = HashMap<Int, HashSet<Position3D>>();
        var currentAreaId = 1

        // reverse mapping for connectedAreas
        val positionToAreaId = HashMap<Position3D, Int>()

        for ((k, v) in area.blocks){
            if(k.x == Int.MAX_VALUE && k.y == Int.MAX_VALUE && k.z == Int.MAX_VALUE)
                continue

            // found tile which hasn't been looked at and is floor
            if(!positionToAreaId.contains(k) && v is Floor){

                val floodFill = floodFill(k, area)
                val connectedArea = HashSet<Position3D>()

                connectedArea.add(k)
                positionToAreaId[k] = currentAreaId

                for((position, _) in floodFill){
                    positionToAreaId[position] = currentAreaId
                    connectedArea.add(position)
                }
                connectedAreas[currentAreaId] = connectedArea
                currentAreaId++;
            }
        }

        return Pair(connectedAreas, positionToAreaId)
    }

    private fun connectRooms(area: WFCAreaBuilder){

        val (connectedAreas, positionToAreaId) = findRooms(area)

        val r = Random(0)

        val startAreaId = connectedAreas.keys.random(r)
        // area where we start
        val startArea = connectedAreas[startAreaId]!!

        // position where we start
        var start = startArea.first()

        outer@ while(connectedAreas.count() != 1) {

            val endAreaId = connectedAreas.keys.random(r)

            if (startAreaId == endAreaId) continue

            // area where we end
            val endArea = connectedAreas[endAreaId]!!

            // position where we end
            val end = endArea.first()

            // how much should we move in x and y to reach the end area
            val delta = (end - start)

            val directionX = sign(delta.x.toFloat()).toInt()
            val directionY = sign(delta.y.toFloat()).toInt()

            // move on x first
            for (x in 0..abs(delta.x)) {

                // calculate position to place corridor block
                val corridorPosition = Position3D.create(start.x + x * directionX, start.y, start.z)
                val block = area.blocks[corridorPosition]

                // if it is wall just put floor tile
                if (block is Wall) {
                    area.blocks[corridorPosition] = Floor()
                    positionToAreaId[corridorPosition] = startAreaId
                    startArea.add(corridorPosition)
                }
                // if it is floor and not part of start area
                else if (!startArea.contains(corridorPosition)){
                    val foundAreaId = positionToAreaId[corridorPosition]

                    // find which area it's part of
                    val foundArea = connectedAreas[foundAreaId]!!

                    // add all tile in found area to start area
                    startArea.addAll(foundArea)

                    // remove from connected areas
                    connectedAreas.remove(foundAreaId)

                    // update auxiliary structure
                    for (position in foundArea) {
                        positionToAreaId[position] = startAreaId
                    }

                    // set some position in the newly reached area as start
                    start = foundArea.random(r)
                    continue@outer // break out to outer loop
                }
            }

            // same as for x
            for (y in 0..abs(delta.y)) {
                val corridorPosition = Position3D.create(end.x, start.y + y * directionY, start.z)
                val block = area.blocks[corridorPosition]
                if (block !is Floor) {
                    area.blocks[corridorPosition] = Floor()
                    positionToAreaId[corridorPosition] = startAreaId
                    startArea.add(corridorPosition)
                }else if (!startArea.contains(corridorPosition)){
                    val foundAreaId = positionToAreaId[corridorPosition]
                    val foundArea = connectedAreas[foundAreaId]!!
                    startArea.addAll(foundArea)
                    connectedAreas.remove(foundAreaId)
                    for (position in foundArea) {
                        positionToAreaId[position] = startAreaId
                    }
                    start = foundArea.random(r)
                    continue@outer
                }
            }
        }
    }
}