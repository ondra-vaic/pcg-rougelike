package cz.cuni.gamedev.nail123.roguelike.tiles

import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import cz.cuni.gamedev.nail123.roguelike.extensions.shift
import cz.cuni.gamedev.nail123.roguelike.world.Direction
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KProperty

class Autotiling(val default: Tile, vararg val mapping: Pair<Int, Tile>) {
    operator fun getValue(wall: Wall, property: KProperty<*>): Tile {
        var flags = 0
        for (direction in Direction.eightDirections) {
            if (wall.area[wall.position shift direction]?.javaClass == wall.javaClass) {
                flags = flags or direction.flag
            }
        }

        for ((mapFlags, tile) in mapping) {
            // Check if all flags are satisfied
            if (mapFlags and flags == mapFlags) {
                return tile
            }
        }
        return default
    }

    operator fun setValue(wall: Wall, property: KProperty<*>, tile: Tile) {
        // Do nothing
    }
}