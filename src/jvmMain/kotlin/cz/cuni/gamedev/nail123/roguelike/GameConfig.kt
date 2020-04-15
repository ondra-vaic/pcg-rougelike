package cz.cuni.gamedev.nail123.roguelike

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size3D

object GameConfig {
    const val DUNGEON_LEVELS = 2
    val TILESET = CP437TilesetResources.rogueYun16x16()
    val THEME = ColorThemes.zenburnVanilla()

    const val SIDEBAR_WIDTH = 18
    const val LOG_AREA_HEIGHT = 8

    const val WINDOW_WIDTH = 80
    const val WINDOW_HEIGHT = 50

    val WORLD_SIZE = Size3D.create(WINDOW_WIDTH, WINDOW_HEIGHT, 2)
    val VISIBLE_SIZE = Size3D.create(
            WINDOW_WIDTH - SIDEBAR_WIDTH,
            WINDOW_HEIGHT - LOG_AREA_HEIGHT,
            2
    )

    fun buildAppConfig() = AppConfig.newBuilder()
            .enableBetaFeatures()
            .withSize(WINDOW_WIDTH, WINDOW_HEIGHT)
            .withDefaultTileset(TILESET)
            .build()
}