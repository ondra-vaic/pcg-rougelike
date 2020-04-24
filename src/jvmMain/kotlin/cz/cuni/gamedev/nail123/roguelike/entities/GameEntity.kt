package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.events.EntityMoved
import cz.cuni.gamedev.nail123.roguelike.extensions.shift
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import cz.cuni.gamedev.nail123.roguelike.world.Area
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile

abstract class GameEntity(val tile: Tile = Tile.empty()) {
    enum class MovementResult { SUCCESS, POSITION_BLOCKED }
    lateinit var area: Area
    open var position: Position3D = Position3D.unknown()
    var lastMovement: Direction? = null

    val x: Int
        get() = position.x
    val y: Int
        get() = position.y
    val z: Int
        get() = position.z


    open fun update() {}

    fun move(direction: Direction): MovementResult {
        lastMovement = direction

        val nextBlock = area[position shift direction]
        if (nextBlock?.blocksMovement == false) {
            moveTo(position shift direction)
            return MovementResult.SUCCESS
        }
        return MovementResult.POSITION_BLOCKED
    }

    fun moveTo(targetPosition: Position3D) {
        area.removeEntity(this)
        area.addEntity(this, targetPosition)
        EntityMoved(this).emit()
    }

    override fun toString(): String = this.javaClass.simpleName
}