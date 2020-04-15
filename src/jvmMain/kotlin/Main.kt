import ch.qos.logback.classic.Level
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.application.SwingApplication
import org.slf4j.LoggerFactory


fun main() {
    (LoggerFactory.getLogger(SwingApplication::class.java) as ch.qos.logback.classic.Logger).level = Level.WARN

    val appConfig = AppConfig.newBuilder()
            .withSize(20, 8)
            .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
            .build()

    val tileGrid = SwingApplications.startTileGrid(appConfig)

    val screen = Screen.create(tileGrid)

    val header = Components.header()
            .withText("Hello, world!")
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)

    screen.theme = ColorThemes.adriftInDreams()
    screen.addComponent(header)

    screen.display()
}