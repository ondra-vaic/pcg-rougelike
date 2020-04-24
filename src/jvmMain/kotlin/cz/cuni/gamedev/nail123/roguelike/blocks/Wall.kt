package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Wall: GameBlock(GameTiles.WALL) {
    override val blocksMovement = true
}