package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.GameBlock
import cz.cuni.gamedev.nail123.roguelike.builders.GameBlockFactory
import cz.cuni.gamedev.nail123.roguelike.extensions.floorNeighbors
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class WorldBuilder(val worldSize: Size3D) {
    val width
        get() = worldSize.xLength
    val height
        get() = worldSize.zLength
    val allPositions
        get() = worldSize.fetchPositions()

    var blocks = mutableMapOf<Position3D, GameBlock>()

    fun makeCaves() = randomizeTiles().smoothen(8)

    fun build(visibleSize: Size3D): World = World(blocks, visibleSize, worldSize)

    fun randomizeTiles() = also {
        allPositions.forEach { pos ->
            blocks[pos] = if (Math.random() < 0.5) GameBlockFactory.floor() else GameBlockFactory.wall()
        }
    }

    fun smoothen(iterations: Int) = also {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()

        repeat(iterations) {
            allPositions.forEach { pos ->
                val neighbors = pos.floorNeighbors()
                val walls = neighbors.filter { blocks[it]?.isWall == true }.count()
                val floors = neighbors.count() - walls

                newBlocks[pos] = if (floors >= walls) GameBlockFactory.floor() else GameBlockFactory.wall()
            }
            blocks = newBlocks
        }
    }

}