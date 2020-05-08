package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasVision
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Player: MovingEntity(GameTiles.PLAYER), HasVision {
    override val visionRadius = 9
    override val blocksMovement = true
    override val blocksVision = false
}