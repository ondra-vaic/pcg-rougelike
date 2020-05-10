package cz.cuni.gamedev.nail123.roguelike.actions

import cz.cuni.gamedev.nail123.roguelike.entities.items.Item
import cz.cuni.gamedev.nail123.roguelike.events.logMessage
import cz.cuni.gamedev.nail123.roguelike.world.Area

class PickUp: GameAction() {
    override fun tryPerform(area: Area): Boolean {
        val playerPos = area.player.position
        val item = area[playerPos]!!.entities.filterIsInstance<Item>().firstOrNull()
        if (item == null) {
            logMessage("There is nothing to pick up here.")
            return false
        }

        // We directly try to equip - we don't need to deal with inventory management then
        val result = area.player.inventory.tryEquip(item)
        if (!result.success) {
            logMessage(result.message)
            return false
        }

        logMessage("Equipped $item")
        return true
    }
}