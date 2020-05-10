package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasCombatStats
import org.hexworks.zircon.api.data.Tile

abstract class Item(tile: Tile): GameEntity(tile) {
    override val blocksMovement = false
    override val blocksVision = false

    abstract fun isEquipable(inventory: Inventory): Inventory.EquipResult
    abstract fun onEquip(character: HasCombatStats)
    abstract fun onUnequip(character: HasCombatStats)
}