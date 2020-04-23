package cz.cuni.gamedev.nail123.roguelike.actions

import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import cz.cuni.gamedev.nail123.roguelike.world.World
import cz.cuni.gamedev.nail123.roguelike.world.plus
import javax.swing.SwingUtilities

class Move(val direction: Direction): GameAction() {
    override fun perform(world: World) {
        world.player.move(direction)
    }
}