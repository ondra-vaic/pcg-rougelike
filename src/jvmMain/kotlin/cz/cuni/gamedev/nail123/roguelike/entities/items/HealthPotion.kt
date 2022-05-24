package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Inventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import kotlin.random.Random

class HealthPotion(val level: Int): Item(GameTiles.HEALTH_POTION) {

    var healthBonus: Int

    init {
        healthBonus = ((level / 2f) * Random.nextInt(6, 12)).toInt()
    }

    override fun isEquipable(character: HasInventory): Inventory.EquipResult {
        return Inventory.EquipResult.Success
    }

    override fun onEquip(character: HasInventory) {
        if (character is Player) {
            character.hitpoints += healthBonus
            character.hitpoints = character.hitpoints.coerceAtMost(character.maxHitpoints)
            character.removeItem(this)
        }
    }

    override fun onUnequip(character: HasInventory) {}
    override fun toString(): String {
        return "Health Potion($healthBonus)"
    }
}