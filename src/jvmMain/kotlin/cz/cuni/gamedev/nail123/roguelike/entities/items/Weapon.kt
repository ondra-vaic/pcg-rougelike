package cz.cuni.gamedev.nail123.roguelike.entities.items

import org.hexworks.zircon.api.data.Tile

abstract class Weapon(tile: Tile): Item(tile) {
    override fun isEquipable(inventory: Inventory): Inventory.EquipResult {
        return if (inventory.equipped.filterIsInstance<Weapon>().isNotEmpty()) {
            Inventory.EquipResult(false, "Cannot equip two weapons")
        } else Inventory.EquipResult.Success
    }
}