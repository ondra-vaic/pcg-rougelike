package cz.cuni.gamedev.nail123.roguelike.world.builders

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.blocks.Floor
import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * A builder that creates an empty area with a border.
 */
class EmptyAreaBuilder(size: Size3D = GameConfig.AREA_SIZE,
                       visibleSize: Size3D = GameConfig.VISIBLE_SIZE): AreaBuilder(size, visibleSize) {

    override fun create(): AreaBuilder {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val isBorder = x == 0 || x == width - 1 || y == 0 || y == height - 1
                blocks[Position3D.create(x, y, 0)] = if (isBorder) Wall() else Floor()
            }
        }

        for(x in -2..2){
            for(y in -2..2){
                blocks[Position3D.create(10 + x, 10 + y, 0)] = Wall()
            }
        }

        blocks[Position3D.create(10 + 2, 10 + 2, 0)] = Floor()
        blocks[Position3D.create(10 + 1, 10 + 1, 0)] = Floor()

        return this
    }
}