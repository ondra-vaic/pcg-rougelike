package cz.cuni.gamedev.nail123.roguelike.world

import org.hexworks.zircon.api.data.Position3D

enum class Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, UP, DOWN;

    val opposite: Direction
        get() = when (this) {
            NORTH -> SOUTH
            NORTH_EAST -> SOUTH_WEST
            EAST -> WEST
            SOUTH_EAST -> NORTH_WEST
            SOUTH -> NORTH
            SOUTH_WEST -> NORTH_EAST
            WEST -> EAST
            NORTH_WEST -> SOUTH_EAST
            UP -> DOWN
            DOWN -> UP
        }
}
