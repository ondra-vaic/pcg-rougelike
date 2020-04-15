package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.builders.GameTileRepository
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(val tile: Tile = GameTileRepository.FLOOR): BaseBlock<Tile>(
        emptyTile = GameTileRepository.FLOOR,
        // Not caring about sides, we'll use top-down projection
        tiles = persistentMapOf(BlockTileType.TOP to tile)) {

    val isFloor: Boolean
        get() = tile == GameTileRepository.FLOOR

    val isWall: Boolean
        get() = tile == GameTileRepository.WALL
}