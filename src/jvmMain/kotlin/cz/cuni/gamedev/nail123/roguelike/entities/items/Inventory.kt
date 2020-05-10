package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasCombatStats

class Inventory(val parentEntity: HasCombatStats) {
    data class EquipResult(val success: Boolean, val message: String) {
        companion object {
            val Success = EquipResult(true, "Success")
        }
    }

    private val _equipped = ArrayList<Item>()
    private val _unequipped = ArrayList<Item>()

    val equipped: List<Item>
        get() = _equipped
    val unequipped: List<Item>
        get() = _unequipped

    fun tryEquip(item: Item): EquipResult {
        if (item in _equipped) return EquipResult(false, "Item already equipped")
        val test = item.isEquipable(this)
        if (test.success) {
            item.block.entities.remove(item)
            _unequipped.remove(item)
            _equipped.add(item)
            item.onEquip(parentEntity)
        }
        return test
    }
}