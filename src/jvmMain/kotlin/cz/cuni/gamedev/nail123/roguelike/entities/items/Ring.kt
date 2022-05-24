package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Inventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Ring(val attack: Int, val maxHealth: Int, val lifeSteal: Float): Item(GameTiles.RING) {
    override fun isEquipable(character: HasInventory): Inventory.EquipResult {
        return if (character.inventory.equipped.filterIsInstance<Ring>().size >= 3) {
            Inventory.EquipResult(false, "Cannot equip more than three rings")
        } else Inventory.EquipResult.Success
    }

    override fun onEquip(character: HasInventory) {
        if (character is Player) {
            character.attack += attack
            character.maxHitpoints += maxHealth
            character.lifeSteal += lifeSteal
        }
    }

    override fun onUnequip(character: HasInventory) {
        if (character is Player) {
            character.attack -= attack
            character.maxHitpoints -= maxHealth
            character.lifeSteal -= lifeSteal
        }
    }

    override fun toString(): String {
        return "Ring(ls $lifeSteal, att $attack, max HP $maxHealth)"
    }
}