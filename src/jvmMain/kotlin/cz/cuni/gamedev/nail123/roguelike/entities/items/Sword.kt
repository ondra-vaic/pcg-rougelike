package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasCombatStats
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Sword(val attackPower: Int): Weapon(GameTiles.SWORD) {
    override fun onEquip(character: HasCombatStats) {
        character.attack += attackPower
    }

    override fun onUnequip(character: HasCombatStats) {
        character.attack -= attackPower
    }

    override fun toString(): String {
        return "Sword($attackPower)"
    }
}