package cz.cuni.gamedev.nail123.roguelike.mechanics

import cz.cuni.gamedev.nail123.roguelike.entities.MovingEntity
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import org.hexworks.zircon.api.data.Position3D
import kotlin.math.abs

object Navigation {
    fun bestDirection(from: Position3D, to: Position3D): Direction {
        val diff = to - from
        return when {
            (diff.x > abs(diff.y)) -> Direction.EAST
            (diff.x < -abs(diff.y)) -> Direction.WEST
            (diff.y > 0) -> Direction.SOUTH
            else -> Direction.NORTH
        }
    }
}

fun MovingEntity.goBlindlyTowards(target: Position3D) {
    move(Navigation.bestDirection(position, target))
}