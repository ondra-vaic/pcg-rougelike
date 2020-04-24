package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.extensions.asNullable
import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea


class Area(startingBlocks: PersistentMap<Position3D, GameBlock>,
           visibleSize: Size3D,
           actualSize: Size3D,
           val player: Player) : BaseGameArea<Tile, GameBlock>(
                visibleSize,
                actualSize,
                initialContents = startingBlocks
            ), IArea {

    override val areaSize
        get() = actualSize
    val visibleWidth
        get() = visibleSize.xLength
    val visibleHeight
        get() = visibleSize.yLength

    val visibleSize2D = Size.create(visibleWidth, visibleHeight)
    private val _entities = mutableListOf<GameEntity>()

    override val entities: List<GameEntity>
        get() = _entities

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)
        }
    }

    override fun get(position: Position3D): GameBlock? = fetchBlockAt(position).asNullable
    override fun addEntity(entity: GameEntity, position: Position3D) {
        blocks[position]?.addEntity(entity)
        _entities.add(entity)
        entity.position = position
        entity.area = this
    }
    override fun removeEntity(entity: GameEntity) {
        blocks[entity.position]?.removeEntity(entity)
        _entities.remove(entity)
    }
}

