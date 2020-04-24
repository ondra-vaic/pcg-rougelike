package cz.cuni.gamedev.nail123.roguelike.entities

/**
 * This describes the interactions interface. Game entities can interact with one another, and this can be specified
 * from both the caller and the callee viewpoints. If both the caller and the callee will define an interaction, only
 * the one specified in the caller will be applied.
 */
interface Interacting {
    fun interactWith(entity: GameEntity)
}

interface Interactable {
    fun acceptInteractFrom(entity: GameEntity)
}