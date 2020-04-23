package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

open class GameBlock(var baseTile: Tile): BaseBlock<Tile>(
        emptyTile = Tile.empty(),
        // Tiles not only make a cube, but also act as 3 layers
        // We consider bottom-layer = FLOOR/WALL, content = ENTITIES, top = FOG_OF_WAR
        tiles = persistentMapOf(BlockTileType.BOTTOM to baseTile)) {

    open val blocksMovement = false

    private val entities = mutableListOf<GameEntity>()

    fun addEntity(entity: GameEntity) {
        entities.add(entity)
        updateTileMap()
    }

    fun removeEntity(entity: GameEntity) {
        entities.remove(entity)
        updateTileMap()
    }

    fun updateTileMap() {
        val topEntity = entities.lastOrNull()
        content = topEntity?.tile ?: baseTile
    }
}