package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.extensions.allPositionsShuffled
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * Interface of common features of WorldBuilder (containing incomplete, in-progress worlds) and World.
 */
interface IWorld {
    val blocks: Map<Position3D, GameBlock>
    val entities: MutableList<GameEntity>

    operator fun get(position: Position3D): GameBlock?

    fun addEntity(entity: GameEntity, position: Position3D) {
        this[position]?.addEntity(entity)
        entities.add(entity)
        entity.location = position
    }

    fun removeEntity(entity: GameEntity) {
        this[entity.location]?.removeEntity(entity)
        entities.remove(entity)
        entity.location = Position3D.unknown()
    }

    fun addAtEmptyPosition(entity: GameEntity, offset: Position3D, size: Size3D): Boolean {
        val emptyPosition = size.allPositionsShuffled()
                .map { pos -> pos + offset }
                .filter { pos ->
                    this[pos]?.isBlocking == false
                }.firstOrNull()

        if (emptyPosition != null) addEntity(entity, emptyPosition)
        return emptyPosition != null
    }
}