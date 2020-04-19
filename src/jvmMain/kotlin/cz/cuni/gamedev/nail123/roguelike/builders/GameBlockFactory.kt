package cz.cuni.gamedev.nail123.roguelike.builders

import cz.cuni.gamedev.nail123.roguelike.GameBlock

object GameBlockFactory {
    fun floor() = GameBlock(GameTileRepository.FLOOR)
    fun wall() = GameBlock(GameTileRepository.WALL)
}