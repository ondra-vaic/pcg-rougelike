package cz.cuni.gamedev.nail123.roguelike.world.builders

import cz.cuni.gamedev.nail123.roguelike.blocks.Floor
import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import org.hexworks.zircon.api.data.Position3D

/**
 * A builder that creates an empty area with a border.
 */
class EmptyAreaBuilder: AreaBuilder() {
    override fun create(): AreaBuilder {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val isBorder = x == 0 || x == width - 1 || y == 0 || y == height - 1
                blocks[Position3D.create(x, y, 0)] = if (isBorder) Wall() else Floor()
            }
        }

        return this
    }
}