package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.GameBlock
import cz.cuni.gamedev.nail123.roguelike.builders.GameTileRepository
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.extensions.allPositionsShuffled
import cz.cuni.gamedev.nail123.roguelike.extensions.asNullable
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
    val entities = mutableListOf<GameEntity>()

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)
        }
    }

    fun addEntity(entity: GameEntity, position: Position3D) {
        fetchBlockAt(position).ifPresent {
            it.addEntity(entity)
        }
        entities.add(entity)
    }

    fun addAtEmptyPosition(entity: GameEntity, offset: Position3D, size: Size3D): Boolean {
        val emptyPosition = size.allPositionsShuffled()
            .map { it + offset }
            .filter {
                fetchBlockAt(it).asNullable?.isFloor == true
            }.firstOrNull()

        if (emptyPosition != null) addEntity(entity, emptyPosition)
        return emptyPosition != null
    }
}

