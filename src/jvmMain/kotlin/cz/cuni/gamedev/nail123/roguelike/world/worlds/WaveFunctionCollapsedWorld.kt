package cz.cuni.gamedev.nail123.roguelike.world.worlds

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.entities.Stairs
import cz.cuni.gamedev.nail123.roguelike.pathfinding.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.world.Area
import cz.cuni.gamedev.nail123.roguelike.world.builders.wavefunctioncollapse.WFCAreaBuilder
import org.hexworks.zircon.api.data.Position3D

class WaveFunctionCollapsedWorld: DungeonWorld() {
    override fun buildLevel(floor: Int): Area {
        val area = WFCAreaBuilder(GameConfig.AREA_SIZE).create()

        area.addAtEmptyPosition(
            area.player,
            Position3D.create(0, 0, 0),
            GameConfig.VISIBLE_SIZE
        )

        // Add stairs up
        if (floor > 0) area.addEntity(Stairs(false), area.player.position)

        // Add stairs down
        val floodFill = Pathfinding.floodFill(area.player.position, area)
        val staircasePosition = floodFill.keys.random()
        area.addEntity(Stairs(), staircasePosition)

        return area.build()
    }
}