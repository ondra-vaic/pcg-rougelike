package cz.cuni.gamedev.nail123.roguelike.world.builders.wavefunctioncollapse

import com.github.jroskopf.wfc.OverlappingModel
import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.blocks.Floor
import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import cz.cuni.gamedev.nail123.roguelike.entities.placeable.Door
import cz.cuni.gamedev.nail123.roguelike.world.builders.AreaBuilder
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Position3D
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import kotlin.random.Random

class WFCAreaBuilder(size: Size3D, visibleSize: Size3D = GameConfig.VISIBLE_SIZE) : AreaBuilder(size, visibleSize) {
    val source = File("src/jvmMain/resources/levels/wfc_sample.txt").readLines()

    val white = Color.WHITE.rgb
    val door = Color.ORANGE.rgb
    val black = Color.BLACK.rgb

    fun charToColor(char: Char) = when(char) {
        '#' -> black
        'D' -> door
        else -> white
    }
    fun colorToBlock(color: Int) = when(color) {
        door -> Floor().apply { entities.add(Door()) }
        black -> Wall()
        else -> Floor()
    }

    override fun create() = apply {
        val inputBitmap = getInputBitmap()
        val width = GameConfig.AREA_SIZE.xLength
        val height = GameConfig.AREA_SIZE.yLength

        val model = collapse(inputBitmap, width, height)

        val outputBitmap = model.graphics()!!
        for (x in 0 until width) {
            for (y in 0 until height) {
                val color = outputBitmap.getRGB(x, y)
                blocks[Position3D.create(x, y, 0)] = colorToBlock(color)
            }
        }
    }

    private fun getInputBitmap(): BufferedImage {
        val bitmap = BufferedImage(source[0].length, source.size, BufferedImage.TRANSLUCENT)

        for ((y, line) in source.withIndex()) {
            for ((x, char) in line.withIndex()) {
                bitmap.setRGB(x, y, charToColor(char))
            }
        }

        return bitmap
    }

    private fun collapse(inputBitmap: BufferedImage, width: Int, height: Int): OverlappingModel {
        // Symmetry 1 - 8, how many symmetries to use (symmetry, rotate, symmetry, rotate, ...)
        val model = OverlappingModel(
                inputBitmap,
                N = 3,
                width = width,
                height = height,
                periodicInput = true,
                periodicOutput = false,
                symmetry = 1,
                ground = 0
        )

        val max_repeats = 10

        for (i in 1..max_repeats) {
            val success = model.run(Random.nextInt(), 1000000)
            if (success) break
            if (!success && i == max_repeats) throw Exception("Wave Function Collapse algorithm didn't find solution!")
        }

        return model
    }

}