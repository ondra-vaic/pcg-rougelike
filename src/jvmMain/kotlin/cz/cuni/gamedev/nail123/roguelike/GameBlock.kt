package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.builders.GameTileRepository
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

open class GameBlock(var baseTile: Tile = GameTileRepository.FLOOR): BaseBlock<Tile>(
        emptyTile = GameTileRepository.FLOOR,
        // Not caring about sides, we'll use top-down projection
        tiles = persistentMapOf(BlockTileType.TOP to baseTile)) {

    val isFloor: Boolean
        get() = baseTile == GameTileRepository.FLOOR

    val isWall: Boolean
        get() = baseTile == GameTileRepository.WALL

    private val currentEntities = mutableListOf<GameEntity>()

    fun addEntity(entity: GameEntity) {
        currentEntities.add(entity)
        updateTileMap()
    }

    fun removeEntity(entity: GameEntity) {
        currentEntities.remove(entity)
        updateTileMap()
    }

    fun updateTileMap() {
        // TODO: change the entity layer to CONTENT and the base layer to BOTTOM
        val topEntity = currentEntities.lastOrNull()
        top = topEntity?.tile ?: baseTile
    }
}