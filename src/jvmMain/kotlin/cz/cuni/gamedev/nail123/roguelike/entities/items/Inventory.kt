package cz.cuni.gamedev.nail123.roguelike.entities.items

class Inventory {
    data class EquipResult(val success: Boolean, val message: String) {
        companion object {
            val Success = EquipResult(true, "Success")
        }
    }

    private val _equipped = ArrayList<Item>()
    val equipped: List<Item>
        get() = _equipped
    private val _unequipped = ArrayList<Item>()
    val unequipped: List<Item>
        get() = _unequipped

    fun tryEquip(item: Item): EquipResult {
        if (item in _equipped) return EquipResult(false, "Item already equipped")
        val test = item.isEquipable(this)
        if (test.success) {
            _unequipped.remove(item)
            _equipped.add(item)
        }
        return test
    }
}