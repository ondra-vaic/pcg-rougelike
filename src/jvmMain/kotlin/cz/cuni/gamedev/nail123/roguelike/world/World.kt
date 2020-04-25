package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.Game
import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.world.builders.AreaBuilder
import cz.cuni.gamedev.nail123.roguelike.world.builders.automata.CellularAutomataAreaBuilder
import cz.cuni.gamedev.nail123.utils.collections.ObservableList
import cz.cuni.gamedev.nail123.utils.collections.withAddListener
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea

/**
 * Represents the whole world of the game, in other words, the game state. Implements a linear progression of levels.
 */
class World {
    val levels = ObservableList<Area>().withAddListener { level -> level.world = this }

    var currentLevel: Int = 0

    val areaSwitcher = AreaSwitcher(GameConfig.VISIBLE_SIZE, GameConfig.AREA_SIZE)
    val currentArea
        get() = areaSwitcher.innerArea

    val player: Player
        get() = currentArea.player

    init {
        levels.add(buildLevel(0))
        currentLevel = 0
        goToArea(levels[0])
    }

    fun buildLevel(floor: Int): Area {
        return CellularAutomataAreaBuilder(GameConfig.AREA_SIZE).build()
    }

    fun nextLevel() {
        ++currentLevel
        println("Going to level $currentLevel")
        if (currentLevel >= levels.size) {
            levels.add(buildLevel(levels.size))
        }
        goToArea(levels[currentLevel])
    }

    fun goToArea(area: Area) {
        areaSwitcher.switchTo(area)
    }
}