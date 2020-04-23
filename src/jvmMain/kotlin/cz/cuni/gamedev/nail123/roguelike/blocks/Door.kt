package cz.cuni.gamedev.nail123.roguelike.blocks

import cz.cuni.gamedev.nail123.roguelike.builders.GameTiles
import org.hexworks.zircon.api.data.Tile

class Door(var isOpen: Boolean): GameBlock(getTile(isOpen)) {
    val tile: Tile
        get() = getTile(isOpen)

    override val blocksMovement: Boolean
        get() = !isOpen

    companion object {
        fun getTile(isOpen: Boolean): Tile =
                if (isOpen) GameTiles.OPEN_DOOR
                else GameTiles.CLOSED_DOOR
    }
}