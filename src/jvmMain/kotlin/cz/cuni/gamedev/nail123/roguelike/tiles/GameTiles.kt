package cz.cuni.gamedev.nail123.roguelike.tiles

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

object GameTiles {
    val defaultCharTileset = CP437TilesetResources.rogueYun16x16()
    val defaultGraphicalTileset = TilesetResources.kenney16x16()

    val EMPTY: Tile = Tile.empty()

    // Allowed characters are CP437 https://en.wikipedia.org/wiki/Code_page_437
    val FLOOR = characterTile('.', GameColors.FLOOR_FOREGROUND, GameColors.FLOOR_BACKGROUND)
    // Wall tile is replaced by autotiling in Wall.kt
    val WALL = characterTile('#', GameColors.WALL_FOREGROUND, GameColors.WALL_BACKGROUND)
    val PLAYER = graphicalTile("Adventurer")
    val CLOSED_DOOR = graphicalTile("Door closed")
    val OPEN_DOOR = graphicalTile("Door open")
    val STAIRS_DOWN = graphicalTile("Stairs down")
    val STAIRS_UP = graphicalTile("Stairs up")

    fun characterTile(char: Char,
                      foreground: TileColor = GameColors.OBJECT_FOREGROUND,
                      background: TileColor = TileColor.transparent()): Tile {

        return Tile.newBuilder()
                   .withCharacter(char)
                   .withForegroundColor(foreground)
                   .withBackgroundColor(background)
                   .build()
    }

    fun graphicalTile(tag: String, tileset: TilesetResource = defaultGraphicalTileset): Tile {
        return Tile.newBuilder()
                   .withName(tag)
                   .withTags(tag.split(' ').toSet())
                   .withTileset(tileset)
                   .buildGraphicalTile()
    }
}