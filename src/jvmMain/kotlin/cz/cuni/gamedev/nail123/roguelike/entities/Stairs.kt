package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Stairs(val leadDown: Boolean = true): GameEntity(
        if (leadDown) GameTiles.STAIRS_DOWN else GameTiles.STAIRS_UP
    ), Interactable {

    override fun acceptInteractFrom(entity: GameEntity): InteractionResult {
        when (entity) {
            is Player -> {
                if (leadDown) area.world.moveDown() else area.world.moveUp()
            }
            else -> return InteractionResult.NO_INTERACTION
        }
        return InteractionResult.INTERACTED
    }
}