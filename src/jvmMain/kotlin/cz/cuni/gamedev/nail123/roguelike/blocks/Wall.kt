package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.builders.GameTileRepository

class Wall: GameBlock(GameTileRepository.WALL) {
    override val isBlocking = true
}