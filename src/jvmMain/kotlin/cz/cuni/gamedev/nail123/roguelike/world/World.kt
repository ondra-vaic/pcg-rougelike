package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.GameBlock
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea

class World(startingBlocks: Map<Position3D, GameBlock>,
            visibleSize: Size3D,
            actualSize: Size3D) : BaseGameArea<Tile, GameBlock>(visibleSize, actualSize, startingBlocks) {

    val width
        get() = actualSize.xLength
    val height
        get() = actualSize.zLength
    val visibleWidth
        get() = visibleSize.xLength
    val visibleHeight
        get() = visibleSize.zLength

    val visibleSize2D = Size.create(visibleWidth, visibleHeight)

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)
        }
    }
}