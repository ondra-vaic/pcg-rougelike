package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.extensions.shift
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile

abstract class MovingEntity(
        tile: Tile = Tile.empty(),
        sortingLayer: SortingLayer = SortingLayer.CHARACTER): GameEntity(tile, sortingLayer) {

    enum class MovementResult { SUCCESS, POSITION_BLOCKED }

    var lastMovement: Direction? = null

    fun move(direction: Direction): MovementResult {
        lastMovement = direction

        val nextBlock = area[position shift direction]
        if (nextBlock?.blocksMovement == false) {
            moveTo(position shift direction)
            interaction(this, nextBlock)
            return MovementResult.SUCCESS
        }
        return MovementResult.POSITION_BLOCKED
    }

    fun moveTo(targetPosition: Position3D) {
        if (this.position != Position3D.unknown())
            area.removeEntity(this)
        area.addEntity(this, targetPosition)
    }
}