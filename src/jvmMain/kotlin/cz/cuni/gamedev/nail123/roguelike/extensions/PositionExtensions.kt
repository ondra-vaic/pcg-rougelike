package cz.cuni.gamedev.nail123.roguelike.extensions

import org.hexworks.zircon.api.data.Position3D

fun Position3D.floorNeighbors(): List<Position3D> {
    return (-1..1).flatMap { deltaX ->
        (-1..1).map { deltaY ->
            Position3D.create(x + deltaX, y + deltaY, z)
        }
    }
}