package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.actions.GameAction
import cz.cuni.gamedev.nail123.roguelike.actions.Move
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import cz.cuni.gamedev.nail123.roguelike.world.World
import cz.cuni.gamedev.nail123.roguelike.world.WorldBuilder
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.UIEvent

/**
 * A class containing a state of the game (World) and the game logic.
 */
class Game(val world: World) {
    enum class PlayerAction {
        MOVE_NORTH,
        MOVE_EAST,
        MOVE_WEST,
        MOVE_SOUTH
    }
    val playerActions = mapOf<PlayerAction, GameAction>(
            PlayerAction.MOVE_NORTH to Move(world.player, Direction.NORTH),
            PlayerAction.MOVE_EAST to Move(world.player, Direction.EAST),
            PlayerAction.MOVE_WEST to Move(world.player, Direction.WEST),
            PlayerAction.MOVE_SOUTH to Move(world.player, Direction.SOUTH)
    )

    // The main game loop
    fun step(action: PlayerAction?) {
        if (action != null) playerActions[action]?.perform(world)
        for (entity in world.entities) entity.update()
    }

    companion object {
        fun create(worldSize: Size3D = GameConfig.WORLD_SIZE,
                   visibleSize: Size3D = GameConfig.VISIBLE_SIZE): Game {

            val world = WorldBuilder(worldSize)
                    .makeCaves()
                    .addPlayer()
                    .build(visibleSize)

            return Game(world)
        }
    }
}