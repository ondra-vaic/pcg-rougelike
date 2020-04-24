package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.extensions.allPositionsShuffled
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * Interface of common features of WorldBuilder (containing incomplete, in-progress worlds) and World.
 */
interface IArea {
    val areaSize: Size3D
    val width
        get() = areaSize.xLength
    val height
        get() = areaSize.yLength
    val allPositions
        get() = areaSize.fetchPositions()
    val blocks: Map<Position3D, GameBlock>
    val entities: List<GameEntity>

    operator fun get(position: Position3D): GameBlock?

    fun addEntity(entity: GameEntity, position: Position3D)
    fun removeEntity(entity: GameEntity)

    fun addAtEmptyPosition(entity: GameEntity, offset: Position3D, size: Size3D): Boolean {
        val emptyPosition = size.allPositionsShuffled()
                .map { pos -> pos + offset }
                .filter { pos ->
                    this[pos]?.blocksMovement == false
                }.firstOrNull()

        if (emptyPosition != null) addEntity(entity, emptyPosition)
        return emptyPosition != null
    }
}