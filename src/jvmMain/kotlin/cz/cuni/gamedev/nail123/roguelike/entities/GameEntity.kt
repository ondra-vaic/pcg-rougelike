package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.extensions.shift
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import cz.cuni.gamedev.nail123.roguelike.world.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile

abstract class GameEntity(val tile: Tile = Tile.empty()) {
    enum class MovementResult { SUCCESS, POSITION_BLOCKED }
    lateinit var world: World
    var position: Position3D = Position3D.unknown()
    var lastMovement: Direction? = null

    open fun update() {}

    fun move(direction: Direction): MovementResult {
        lastMovement = direction

        val nextBlock = world[position shift direction]
        if (nextBlock?.blocksMovement == false) {
            moveTo(position shift direction)
            return MovementResult.SUCCESS
        }
        return MovementResult.POSITION_BLOCKED
    }

    fun moveTo(targetPosition: Position3D) {
        world.removeEntity(this)
        world.addEntity(this, targetPosition)
    }

    override fun toString(): String = this.javaClass.simpleName
}