package cz.cuni.gamedev.nail123.roguelike.actions

import cz.cuni.gamedev.nail123.roguelike.world.Direction
import cz.cuni.gamedev.nail123.roguelike.world.Area

class Move(val direction: Direction): GameAction() {
    override fun perform(area: Area) {
        area.player.move(direction)
    }
}