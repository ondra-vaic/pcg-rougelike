package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasCombatStats
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Interactable
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.InteractionType
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.interactionContext
import cz.cuni.gamedev.nail123.roguelike.events.logMessage
import org.hexworks.zircon.api.data.Tile

abstract class Item(tile: Tile): GameEntity(tile), Interactable {
    override val blocksMovement = false
    override val blocksVision = false

    abstract fun isEquipable(inventory: Inventory): Inventory.EquipResult
    abstract fun onEquip(character: HasCombatStats)
    abstract fun onUnequip(character: HasCombatStats)

    override fun acceptInteractFrom(other: GameEntity, type: InteractionType) = interactionContext(other, type) {
        withEntity<Player>(type) { logMessage("Here lies ${this@Item}") }
    }
}