package cz.cuni.gamedev.nail123.roguelike.controls

import cz.cuni.gamedev.nail123.roguelike.Game
import org.hexworks.zircon.api.uievent.KeyCode

class KeyboardConfig(val mapping: Map<KeyCode, Game.PlayerAction>) {
    companion object {
        val Default = KeyboardConfig(mapOf(
            // Default 8-directional movement (WASD + QEZC)
            KeyCode.KEY_W to Game.PlayerAction.MOVE_NORTH,
            KeyCode.KEY_A to Game.PlayerAction.MOVE_WEST,
            KeyCode.KEY_S to Game.PlayerAction.MOVE_SOUTH,
            KeyCode.KEY_D to Game.PlayerAction.MOVE_EAST,
            KeyCode.KEY_E to Game.PlayerAction.MOVE_NORTHEAST,
            KeyCode.KEY_C to Game.PlayerAction.MOVE_SOUTHEAST,
            KeyCode.KEY_Z to Game.PlayerAction.MOVE_SOUTHWEST,
            // Supporting czech keyboard layout
            KeyCode.KEY_Y to Game.PlayerAction.MOVE_SOUTHWEST,
            KeyCode.KEY_Q to Game.PlayerAction.MOVE_NORTHWEST,

            // 4-directional movement also on arrow keys
            KeyCode.UP to Game.PlayerAction.MOVE_NORTH,
            KeyCode.LEFT to Game.PlayerAction.MOVE_WEST,
            KeyCode.DOWN to Game.PlayerAction.MOVE_SOUTH,
            KeyCode.RIGHT to Game.PlayerAction.MOVE_EAST
        ))
    }
}