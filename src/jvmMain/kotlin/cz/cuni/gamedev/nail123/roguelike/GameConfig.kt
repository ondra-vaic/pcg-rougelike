package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import cz.cuni.gamedev.nail123.roguelike.world.World
import cz.cuni.gamedev.nail123.roguelike.world.worlds.WaveFunctionCollapsedWorld
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size3D

object GameConfig {
    fun defaultWorld(): World = WaveFunctionCollapsedWorld()

    const val DUNGEON_LEVELS = 25
    const val GAME_LEVELS = 5
    val THEME = ColorThemes.zenburnVanilla()

    const val SIDEBAR_WIDTH = 35
    const val LOG_AREA_HEIGHT = 8

    const val WINDOW_WIDTH = 97
    const val WINDOW_HEIGHT = 60

    val VISIBLE_WIDTH = WINDOW_WIDTH - SIDEBAR_WIDTH
    val VISIBLE_HEIGHT = WINDOW_HEIGHT - LOG_AREA_HEIGHT

    val VISIBLE_SIZE = Size3D.create(
            VISIBLE_WIDTH,
            VISIBLE_HEIGHT,
            1
    )

    // If you want to have larger-than-screen areas and scrolling, modify this
    val AREA_SIZE = VISIBLE_SIZE
    // e.g. val AREA_SIZE = Size3D.create(100, 100, 1)

    fun buildAppConfig() = AppConfig.newBuilder()
            .enableBetaFeatures()
            .withSize(WINDOW_WIDTH, WINDOW_HEIGHT)
            .withDefaultTileset(GameTiles.defaultCharTileset)
            .build()
}