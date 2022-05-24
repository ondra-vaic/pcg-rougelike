package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Inventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Shield (override val defense: Int): Armor(defense, GameTiles.SHIELD) {
    override fun isEquipable(character: HasInventory): Inventory.EquipResult {
        return if (character.inventory.equipped.filterIsInstance<Shield>().isNotEmpty()) {
            Inventory.EquipResult(false, "Cannot equip two shields")
        } else Inventory.EquipResult.Success
    }

    override fun toString(): String {
        return "Shield($defense)"
    }
}