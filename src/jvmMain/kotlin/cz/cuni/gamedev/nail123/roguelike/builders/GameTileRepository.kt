package cz.cuni.gamedev.nail123.roguelike.builders

import cz.cuni.gamedev.nail123.roguelike.builders.GameColors
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {
    val EMPTY: Tile = Tile.empty()

    // Allowed characters are CP437 https://en.wikipedia.org/wiki/Code_page_437
    val FLOOR: Tile = Tile.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(GameColors.FLOOR_FOREGROUND)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .build()

    val WALL: Tile = Tile.newBuilder()
            .withCharacter('#')
            .withForegroundColor(GameColors.WALL_FOREGROUND)
            .withBackgroundColor(GameColors.WALL_BACKGROUND)
            .build()

    val PLAYER: Tile = Tile.newBuilder()
            .withCharacter('@')
            .withForegroundColor(GameColors.ACCENT_COLOR)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .build()

    val CLOSED_DOOR: Tile = Tile.newBuilder()
            .withCharacter('D')
            .withForegroundColor(GameColors.DOOR_FOREGROUND)
            .withBackgroundColor(GameColors.WALL_BACKGROUND)
            .build()

    val OPEN_DOOR: Tile = Tile.newBuilder()
            .withCharacter('d')
            .withForegroundColor(GameColors.DOOR_FOREGROUND)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .build()
}