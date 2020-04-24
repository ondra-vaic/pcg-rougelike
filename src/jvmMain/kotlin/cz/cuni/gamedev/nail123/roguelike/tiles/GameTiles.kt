package cz.cuni.gamedev.nail123.roguelike.tiles

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

object GameTiles {
    val EMPTY: Tile = Tile.empty()

    // Allowed characters are CP437 https://en.wikipedia.org/wiki/Code_page_437
    val FLOOR = characterTile('.', GameColors.FLOOR_FOREGROUND, GameColors.FLOOR_BACKGROUND)
    val WALL = characterTile('#', GameColors.WALL_FOREGROUND, GameColors.WALL_BACKGROUND)
    val PLAYER = characterTile('@', GameColors.ACCENT_COLOR)
    val CLOSED_DOOR = characterTile('+', GameColors.DOOR_FOREGROUND)
    val OPEN_DOOR_VERTICAL = characterTile('|', GameColors.DOOR_FOREGROUND)
    val OPEN_DOOR_HORIZONTAL = characterTile('-', GameColors.DOOR_FOREGROUND)
    val STAIRS_DOWN = characterTile('>', GameColors.STAIRS_FOREGROUND, GameColors.FLOOR_BACKGROUND)
    val STAIRS_UP = characterTile('<', GameColors.STAIRS_FOREGROUND, GameColors.FLOOR_BACKGROUND)

    fun characterTile(char: Char,
                      foreground: TileColor = GameColors.OBJECT_FOREGROUND,
                      background: TileColor = TileColor.transparent()): Tile {

        return Tile.newBuilder()
                   .withCharacter(char)
                   .withForegroundColor(foreground)
                   .withBackgroundColor(background)
                   .build()
    }

    fun graphicalTile(tag: String, tileset: TilesetResource): Tile {
        return Tile.newBuilder()
                   .withName(tag)
                   .withTags(tag.split(' ').toSet())
                   .withTileset(tileset)
                   .buildGraphicalTile()
    }
}