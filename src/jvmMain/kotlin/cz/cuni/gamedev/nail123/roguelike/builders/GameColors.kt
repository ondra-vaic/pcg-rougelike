package cz.cuni.gamedev.nail123.roguelike.builders

import org.hexworks.zircon.api.color.TileColor

object GameColors {
    val WALL_FOREGROUND = TileColor.fromString("#75715E")
    val WALL_BACKGROUND = TileColor.fromString("#3E3D32")

    val FLOOR_FOREGROUND = TileColor.fromString("#75715E")
    val FLOOR_BACKGROUND = TileColor.fromString("#1e2320")

    val ACCENT_COLOR = TileColor.fromString("#FFCD22")
    val OBJECT_FOREGROUND = TileColor.fromString("#FCA903")

    val DOOR_FOREGROUND = TileColor.fromString("#AD6200")
}