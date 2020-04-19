package cz.cuni.gamedev.nail123.roguelike.entities

import cz.cuni.gamedev.nail123.roguelike.world.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile

abstract class GameEntity(val tile: Tile = Tile.empty()) {
    var world: World? = null
    var location: Position3D = Position3D.unknown()

    open fun update() {}
}