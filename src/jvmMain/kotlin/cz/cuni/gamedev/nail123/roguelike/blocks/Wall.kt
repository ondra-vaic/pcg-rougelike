package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.builders.GameTiles

class Wall: GameBlock(GameTiles.WALL) {
    override val blocksMovement = true
}