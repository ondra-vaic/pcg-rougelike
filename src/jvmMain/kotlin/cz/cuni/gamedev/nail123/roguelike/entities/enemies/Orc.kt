package cz.cuni.gamedev.nail123.roguelike.entities.enemies

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasVision
import cz.cuni.gamedev.nail123.roguelike.mechanics.Vision
import cz.cuni.gamedev.nail123.roguelike.mechanics.goSmartlyTowards
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Orc(override val maxHitpoints: Int, override var attack: Int, override var defense: Int, override val visionRadius: Int): Enemy(GameTiles.ORC), HasVision {

    override val blocksVision = false
    override var hitpoints = maxHitpoints

    var hasSeenPlayer = false

    override fun update() {
        // Get the player position
        val playerPosition = area.player.position
        // Use the Vision mechanic to get visible positions
        val canSeePlayer = playerPosition in Vision.getVisiblePositionsFrom(area, position, visionRadius)
        // If he sees player, he will start navigating toward him and never loses track
        if (canSeePlayer) hasSeenPlayer = true
        if (hasSeenPlayer) {
            goSmartlyTowards(playerPosition)
        }
    }

}
