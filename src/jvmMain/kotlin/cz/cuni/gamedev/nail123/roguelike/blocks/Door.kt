package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.builders.GameTileRepository
import org.hexworks.zircon.api.data.Tile

class Door(var isOpen: Boolean): GameBlock(getTile(isOpen)) {
    val tile: Tile
        get() = getTile(isOpen)

    override val isBlocking: Boolean
        get() = !isOpen

    companion object {
        fun getTile(isOpen: Boolean): Tile =
                if (isOpen) GameTileRepository.OPEN_DOOR
                else GameTileRepository.CLOSED_DOOR
    }
}