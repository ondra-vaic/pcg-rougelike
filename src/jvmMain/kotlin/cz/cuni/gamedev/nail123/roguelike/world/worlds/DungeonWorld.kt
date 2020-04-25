package cz.cuni.gamedev.nail123.roguelike.world.worlds

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.entities.Stairs
import cz.cuni.gamedev.nail123.roguelike.pathfinding.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.world.Area
import cz.cuni.gamedev.nail123.roguelike.world.World
import cz.cuni.gamedev.nail123.roguelike.world.builders.automata.CellularAutomataAreaBuilder

/**
 * This world provides infinite-depth dungeon delving.
 */
open class DungeonWorld: World() {
    var currentLevel: Int = 0
    val levels
        get() = areas

    init {
        levels.add(buildLevel(0))
        goToArea(levels[0])
    }

    fun buildLevel(floor: Int): Area {
        val area = CellularAutomataAreaBuilder(GameConfig.AREA_SIZE).create()

        val floodFill = Pathfinding.floodFill(area.player.position, area)
        val max = floodFill.values.max()!!
        val staircasePosition = floodFill.filter { (_, dist) -> dist == max / 2 }.toList().random().first

        area.addEntity(Stairs(), staircasePosition)

        return area.build()
    }

    override fun moveDown() {
        ++currentLevel
        println("Going to level $currentLevel")
        if (currentLevel >= areas.size) levels.add(buildLevel(levels.size))

        goToArea(levels[currentLevel])
    }

    override fun moveUp() {
        --currentLevel
        goToArea(levels[currentLevel])
    }
}