package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Inventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Ring(override val defense: Int, val attack: Int, val maxHealth: Int): Armor(defense, GameTiles.RING) {
    override fun isEquipable(character: HasInventory): Inventory.EquipResult {
        return if (character.inventory.equipped.filterIsInstance<Ring>().size > 3) {
            Inventory.EquipResult(false, "Cannot equip more than three rings")
        } else Inventory.EquipResult.Success
    }

    override fun onEquip(character: HasInventory) {
        super.onEquip(character)
        if (character is Player) {
            character.attack += attack
            character.maxHitpoints += maxHealth
        }
    }

    override fun onUnequip(character: HasInventory) {
        super.onUnequip(character)
        if (character is Player) {
            character.attack -= attack
            character.maxHitpoints -= maxHealth
        }
    }

    override fun toString(): String {
        return "Ring(def $defense, att $attack, max HP $maxHealth)"
    }
}