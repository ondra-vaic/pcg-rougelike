package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Inventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Body (override val defense: Int): Armor(defense, GameTiles.BODY) {
    override fun isEquipable(character: HasInventory): Inventory.EquipResult {
        return if (character.inventory.equipped.filterIsInstance<Body>().isNotEmpty()) {
            Inventory.EquipResult(false, "Cannot equip two body armors")
        } else Inventory.EquipResult.Success
    }

    override fun toString(): String {
        return "Body($defense)"
    }
}