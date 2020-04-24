package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.world.builders.automata.CellularAutomataAreaBuilder
import cz.cuni.gamedev.nail123.utils.collections.ObservableList
import org.hexworks.cobalt.events.api.KeepSubscription

/**
 * Represents the whole world of the game, in other words, the game state. Implements a linear progression of levels.
 */
class World {
    val size = GameConfig.WORLD_SIZE
    val levelSize = size.to2DSize()

    val levels = ObservableList<Area>()

    var currentLevel = 0
        protected set
    val currentArea
        get() = levels[currentLevel]

    val player: Player
        get() = currentArea.player

    init {
        levels.onAdd { it.world = this }
        levels.add(buildLevel(0))
    }

    fun buildLevel(floor: Int): Area {
        return CellularAutomataAreaBuilder(levelSize.to3DSize(1)).build()
    }

    fun nextLevel() {
        ++currentLevel
        if (currentLevel >= levels.size) {
            levels.add(buildLevel(levels.size))
        }
    }
}