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
            KeyCode.KEY_D to Game.PlayerAction.MOVE_EAST
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