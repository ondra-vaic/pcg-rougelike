package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import cz.cuni.gamedev.nail123.roguelike.tiles.wallTiling
import org.hexworks.zircon.api.data.Tile

class Wall: GameBlock(GameTiles.EMPTY) {
    override val blocksMovement = true

    override var baseTile: Tile by wallTiling
}