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
import org.hexworks.zircon.api.builder.component.GameComponentBuilder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea

/**
 * Represents the whole world of the game, in other words, the game state. Implements a linear progression of levels.
 */
abstract class World {
    val areas = ObservableList<Area>().withAddListener { area -> area.world = this }

    private val areaSwitcher = AreaSwitcher(GameConfig.VISIBLE_SIZE, GameConfig.AREA_SIZE)
    val currentArea
        get() = areaSwitcher.innerArea

    val player: Player
        get() = currentArea.player

    fun goToArea(area: Area) {
        areaSwitcher.switchTo(area)
    }

    abstract fun moveDown()
    abstract fun moveUp()

    companion object {
        fun GameComponentBuilder<Tile, GameBlock>.withWorld(world: World) = this.withGameArea(world.areaSwitcher)
    }
}