package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasCombatStats
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasVision
import cz.cuni.gamedev.nail123.roguelike.entities.items.Inventory
import cz.cuni.gamedev.nail123.roguelike.events.GameOver
import cz.cuni.gamedev.nail123.roguelike.events.logMessage
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Player: MovingEntity(GameTiles.PLAYER), HasVision, HasCombatStats {
    override val visionRadius = 9
    override val blocksMovement = true
    override val blocksVision = false

    override var maxHitpoints = 10
    override var hitpoints = 10
    override var attack = 5
    override var defense = 1

    val inventory = Inventory(this)

    override fun die() {
        super.die()
        this.logMessage("You have died!")
        GameOver(this).emit()
    }
}