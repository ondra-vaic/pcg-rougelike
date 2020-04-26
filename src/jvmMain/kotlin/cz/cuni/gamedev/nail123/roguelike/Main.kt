package cz.cuni.gamedev.nail123.roguelike

import com.github.jroskopf.wfc.OverlappingModel
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main() {
    println("Hello, world!")
    val exampleLines = File("src/jvmMain/resources/levels/wfc_sample.txt").readLines()
    val bitmap = BufferedImage(exampleLines[0].length, exampleLines.size, BufferedImage.TRANSLUCENT)

    val white = Color.WHITE.rgb
    val black = Color.BLACK.rgb
    for ((y, line) in exampleLines.withIndex()) {
        for ((x, char) in line.withIndex()) {
            bitmap.setRGB(x, y, if (char == '#') black else white)
        }
    }

    // Symmetry 0 - 8, how many symmetries to use
    val model = OverlappingModel(
            bitmap,
            3,
            64,
            48,
            true,
            false,
            1,
            0
    )

    val success = model.run(Random.nextInt(), 1000000)

    if (success) {
        val image = model.graphics()
        val outputFile = File("out/wfc.png")
        ImageIO.write(image, "png", outputFile)
    }
//    val file = File("out/bitmap.png")
//    file.mkdirs()
//    ImageIO.write(bitmap, "png", file)
}