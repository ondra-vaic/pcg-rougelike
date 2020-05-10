package cz.cuni.gamedev.nail123.roguelike.gui.fragments

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.world.World
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class InventoryFragment(val world: World): Fragment {
    val inventoryBox = Components.paragraph()
            .withSize(GameConfig.SIDEBAR_WIDTH - 2, 8)
            .build()

    override val root = Components.vbox()
            .withSize(GameConfig.SIDEBAR_WIDTH - 2, 10)
            .withPosition(0, 10)
            .withSpacing(0)
            .build().apply {
                addComponent(Components.header().withText("Inventory").build())
                addComponent(inventoryBox)
            }

    fun update() {
        val inventory = world.player.inventory
        if (inventory.equipped.isEmpty()) {
            inventoryBox.text = "None"
        } else {
            inventoryBox.text = inventory.equipped.map { item -> item.toString() }.joinToString("\n")
        }
    }
}