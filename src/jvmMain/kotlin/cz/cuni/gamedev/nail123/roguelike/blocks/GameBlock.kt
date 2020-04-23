package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.builders.GameTiles
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

open class GameBlock(var baseTile: Tile): BaseBlock<Tile>(
        emptyTile = GameTiles.FLOOR,
        // Not caring about sides, we'll use top-down projection
        tiles = persistentMapOf(BlockTileType.TOP to baseTile)) {

    open val blocksMovement = false

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