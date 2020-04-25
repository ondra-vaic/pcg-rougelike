package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock

enum class InteractionResult {
    INTERACTED, NO_INTERACTION
}
/**
 * This describes the interactions interface. Game entities can interact with one another, and this can be specified
 * from both the caller and the callee viewpoints. If both the caller and the callee will define an interaction, only
 * the one specified in the caller will be applied.
 */
interface Interacting {
    fun interactWith(entity: GameEntity): InteractionResult
}

interface Interactable {
    fun acceptInteractFrom(entity: GameEntity): InteractionResult
}

/**
 * Perform an interaction of an entity and a target block. Currently only first-found is performed, but this may change.
 */
fun interaction(source: GameEntity, target: GameBlock) {
    if (source is Interacting) {
        target.entities.forEach {
            if (source.interactWith(it) == InteractionResult.INTERACTED) return
        }
    }
    target.entities.filterIsInstance<Interactable>().forEach {
        if (it.acceptInteractFrom(source) == InteractionResult.INTERACTED) return
    }
}