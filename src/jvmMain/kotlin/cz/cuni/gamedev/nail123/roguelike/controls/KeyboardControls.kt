package cz.cuni.gamedev.nail123.roguelike.controls

import cz.cuni.gamedev.nail123.roguelike.Game
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse

class KeyboardControls(val game: Game): Controls() {
    // TODO: this should be what KeyboardConfig is for
    val mapping = mapOf(
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
            KeyCode.UP to Game.PlayerAction.MOVE_NORTH,
            KeyCode.LEFT to Game.PlayerAction.MOVE_WEST,
            KeyCode.DOWN to Game.PlayerAction.MOVE_SOUTH,
            KeyCode.RIGHT to Game.PlayerAction.MOVE_EAST
    )

    override fun handleInput(input: UIEvent): UIEventResponse {
        // Validate whether the key pressed is valid
        if (input !is KeyboardEvent) return UIEventResponse.pass()
        val action = mapping[input.code] ?: return UIEventResponse.pass()
        // Perform the action
        game.step(action)
        return UIEventResponse.processed()
    }
}