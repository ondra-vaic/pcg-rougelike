package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.actions.GameAction
import cz.cuni.gamedev.nail123.roguelike.actions.Move
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import cz.cuni.gamedev.nail123.roguelike.world.World
import cz.cuni.gamedev.nail123.roguelike.world.builders.automata.CellularAutomataAreaBuilder
import cz.cuni.gamedev.nail123.roguelike.world.worlds.DungeonWorld

/**
 * A class containing a state of the game (World) and the game logic.
 */
class Game(val world: World = DungeonWorld()) {
    val area
        get() = world.currentArea

    /** The possible actions the player may perform. */
    enum class PlayerAction(val action: GameAction) {
        MOVE_NORTH(Move(Direction.NORTH)),
        MOVE_EAST(Move(Direction.EAST)),
        MOVE_WEST(Move(Direction.WEST)),
        MOVE_SOUTH(Move(Direction.SOUTH)),
        MOVE_NORTHEAST(Move(Direction.NORTH_EAST)),
        MOVE_SOUTHEAST(Move(Direction.SOUTH_EAST)),
        MOVE_SOUTHWEST(Move(Direction.SOUTH_WEST)),
        MOVE_NORTHWEST(Move(Direction.NORTH_WEST))
    }

    // The main game loop
    fun step(playerAction: PlayerAction?) {
        playerAction?.action?.perform(area)
        for (entity in area.entities) entity.update()
    }
}