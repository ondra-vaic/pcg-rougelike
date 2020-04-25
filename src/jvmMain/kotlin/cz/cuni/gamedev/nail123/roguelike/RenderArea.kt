package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import cz.cuni.gamedev.nail123.roguelike.world.Area
import cz.cuni.gamedev.nail123.roguelike.world.worlds.DungeonWorld
import org.hexworks.zircon.internal.tileset.SwingTilesetLoader
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val dungeonWorld = DungeonWorld()
    dungeonWorld.currentArea.toPNG("out/render.png")
}

fun Area.toPNG(filepath: String) {
    val image = BufferedImage(
            width * GameConfig.TILESET.width,
            height * GameConfig.TILESET.height,
            BufferedImage.TRANSLUCENT
    )
    val graphics = image.createGraphics()

    val loader = SwingTilesetLoader()
    val tileset = loader.loadTilesetFrom(GameConfig.TILESET)

    allPositions.forEach { position ->
        val block = get(position) ?: Wall()

        arrayOf(block.bottom, block.content, block.top).forEach {
            tileset.drawTile(it, graphics, position.to2DPosition())
        }
    }

    val file = File(filepath)
    file.mkdirs()
    ImageIO.write(image, "png", file)
}