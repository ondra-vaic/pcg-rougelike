package cz.cuni.gamedev.nail123.roguelike.world

enum class Direction(val flag: Int) {
    NORTH(1), WEST(2), EAST(4), SOUTH(8), NORTH_WEST(16), NORTH_EAST(32),
    SOUTH_WEST(64), SOUTH_EAST(128), UP(256), DOWN(512);

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

    operator fun plus (other:Direction): Int = this.flag + other.flag

    companion object {
        val eightDirections = arrayOf(NORTH, WEST, EAST, SOUTH, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST)
        val fourDirections = arrayOf(NORTH, WEST, EAST, SOUTH)

    }
}
