package cz.cuni.gamedev.nail123.roguelike.mechanics

import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasVision
import cz.cuni.gamedev.nail123.roguelike.world.Area
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.shape.LineFactory
import kotlin.math.sqrt

object Vision {
    fun getVisiblePositionsFrom(area: Area, pos: Position3D, radius: Int): MutableList<Position3D> {
        val results = mutableListOf<Position3D>()
        for (x in -radius .. radius) {
            for (y in -radius .. radius) {
                // Validate position within radius and within area
                if (sqrt((x * x + y * y).toDouble()) > radius) continue
                val candidate = Position3D.create(pos.x + x, pos.y + y, pos.z)
                if (!area.hasBlockAt(candidate)) continue
                // Validate line of sight
                val line = LineFactory
                        .buildLine(pos.to2DPosition(), Position.create(pos.x + x, pos.y + y))
                        .toList()

                val sightBlocked = line.dropLast(1).any { area[it.to3DPosition(pos.z)]?.blocksVision != false }
                if (sightBlocked) continue

                results.add(candidate)
            }
        }
        return results
    }
}

fun <Entity> Entity.visiblePositions(): MutableList<Position3D> where Entity: GameEntity, Entity: HasVision =
        Vision.getVisiblePositionsFrom(area, position, visionRadius)