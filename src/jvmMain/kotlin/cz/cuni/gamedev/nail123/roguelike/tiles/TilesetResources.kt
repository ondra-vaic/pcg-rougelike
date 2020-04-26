package cz.cuni.gamedev.nail123.roguelike.tiles

import org.hexworks.zircon.api.GraphicalTilesetResources

object TilesetResources {
    // TODO this should be more of a manager class
    fun filty32x32() =
        GraphicalTilesetResources.loadTilesetFromJar(32, 32, "/tilesets/filty_32x32/filty_32x32.zip")

    fun kenney16x16() =
        GraphicalTilesetResources.loadTilesetFromJar(16, 16,
                "/tilesets/kenney_superscaled_16x16/kenney_superscaled_16x16.zip"
        )
}