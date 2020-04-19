package cz.cuni.gamedev.nail123.roguelike.world

import org.hexworks.zircon.api.data.Position3D

enum class Direction {
    NORTH, EAST, SOUTH, WEST, UP, DOWN;

    val opposite: Direction
        get() = when (this) {
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
            UP -> DOWN
            DOWN -> UP
        }
}

operator fun Position3D.plus (direction: Direction): Position3D {
    return when (direction) {
        Direction.NORTH -> withRelativeY(-1)
        Direction.EAST -> withRelativeX(1)
        Direction.SOUTH -> withRelativeY(1)
        Direction.WEST -> withRelativeX(-1)
        Direction.UP -> withRelativeZ(-1)
        Direction.DOWN -> withRelativeZ(-1)
    }
}