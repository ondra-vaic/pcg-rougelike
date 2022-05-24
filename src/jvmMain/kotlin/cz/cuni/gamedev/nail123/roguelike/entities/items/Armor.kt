package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import org.hexworks.zircon.api.data.Tile

abstract class Armor (open val defense: Int, tile: Tile): Item(tile) {
    override fun onEquip(character: HasInventory) {
        if (character is Player) {
            character.defense += defense
        }
    }

    override fun onUnequip(character: HasInventory) {
        if (character is Player) {
            character.defense -= defense
        }
    }
}