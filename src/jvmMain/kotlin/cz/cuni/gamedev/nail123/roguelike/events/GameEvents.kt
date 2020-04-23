package cz.cuni.gamedev.nail123.roguelike.events

import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.Player

/**
 * Event triggered each time an entity moves. Contains a subclass PlayerMoved that triggers when the entity is player.
 */
class EntityMoved(override val emitter: GameEntity): GameEvent() {
    class PlayerMoved(override val emitter: GameEntity): GameEvent()

    override fun emit() {
        super.emit()
        if (emitter is Player) PlayerMoved(emitter).emit()
    }
}
