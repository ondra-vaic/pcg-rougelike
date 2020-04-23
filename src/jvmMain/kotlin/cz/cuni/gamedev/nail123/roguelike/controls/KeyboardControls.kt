package cz.cuni.gamedev.nail123.roguelike.controls

import cz.cuni.gamedev.nail123.roguelike.Game
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse

class KeyboardControls(val game: Game): Controls() {
    var config = KeyboardConfig.Default

    override fun handleInput(input: UIEvent): UIEventResponse {
        // Validate whether the key pressed is valid
        if (input !is KeyboardEvent) return UIEventResponse.pass()
        val action = config.mapping[input.code] ?: return UIEventResponse.pass()
        // Perform the action
        game.step(action)
        return UIEventResponse.processed()
    }
}