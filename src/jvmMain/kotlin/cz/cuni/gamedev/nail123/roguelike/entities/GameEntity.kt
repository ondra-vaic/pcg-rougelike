package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.world.Area
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile

abstract class GameEntity(val tile: Tile = Tile.empty(), val sortingLayer: SortingLayer = SortingLayer.ITEM) {
    lateinit var area: Area

    // Properties allow binding with other properties and also listen to changes
    val positionProperty = createPropertyFrom(Position3D.unknown())
    var position by positionProperty.asDelegate()

    val x: Int
        get() = position.x
    val y: Int
        get() = position.y
    val z: Int
        get() = position.z

    // Gets called after the area is built
    open fun init() {}

    open fun update() {}

    override fun toString(): String = this.javaClass.simpleName
}