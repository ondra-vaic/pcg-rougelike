package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.extensions.asNullable
import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea


class World(startingBlocks: PersistentMap<Position3D, GameBlock>,
            visibleSize: Size3D,
            actualSize: Size3D,
            val player: Player) : BaseGameArea<Tile, GameBlock>(
                visibleSize,
                actualSize,
                initialContents = startingBlocks
            ), IWorld {

    val width
        get() = actualSize.xLength
    val height
        get() = actualSize.zLength
    val visibleWidth
        get() = visibleSize.xLength
    val visibleHeight
        get() = visibleSize.zLength

    val visibleSize2D = Size.create(visibleWidth, visibleHeight)
    override val entities = mutableListOf<GameEntity>()

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)
        }
    }

    override fun get(position: Position3D): GameBlock? = fetchBlockAt(position).asNullable
}

