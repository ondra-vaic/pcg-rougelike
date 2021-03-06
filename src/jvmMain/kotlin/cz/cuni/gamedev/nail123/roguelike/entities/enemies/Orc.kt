package cz.cuni.gamedev.nail123.roguelike.entities.enemies

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasVision
import cz.cuni.gamedev.nail123.roguelike.mechanics.Vision
import cz.cuni.gamedev.nail123.roguelike.mechanics.goSmartlyTowards
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import kotlin.random.Random
import kotlin.random.nextInt

data class OrcParameters(val maxHitpoints: Int, val attack: Int, val defense: Int, val visionRadius: Int)
data class OrcParametersRanges(val maxHitpoints: IntRange, val attack: IntRange, val defense: IntRange, val visionRadius: IntRange)

class Orc(override val level: Int): Enemy(level, GameTiles.ORC), HasVision {

    override val blocksVision = false
    override var hitpoints = 0

    override val maxHitpoints: Int
    override val visionRadius: Int
    override var attack: Int = 0
    override var defense: Int = 0

    var hasSeenPlayer = false

    init {
        val stats = calculateStats(level)
        maxHitpoints = stats.maxHitpoints
        attack = stats.attack
        defense = stats.defense
        visionRadius = stats.visionRadius
        hitpoints = maxHitpoints
    }

    companion object {

        private val orcParametersRanges = mapOf(
            1 to OrcParametersRanges(
                IntRange(15, 20),
                IntRange(3, 5),
                IntRange(0, 1),
                IntRange(5, 8)),
            2 to OrcParametersRanges(
                IntRange(18, 28),
                IntRange(3, 7),
                IntRange(0, 2),
                IntRange(5, 11)),
            3 to OrcParametersRanges(
                IntRange(24, 38),
                IntRange(5, 10),
                IntRange(1, 3),
                IntRange(5, 14)),
            4 to OrcParametersRanges(
                IntRange(36, 48),
                IntRange(8, 14),
                IntRange(2, 4),
                IntRange(8, 14)),
            5 to OrcParametersRanges(
                IntRange(43, 57),
                IntRange(10, 16),
                IntRange(3, 5),
                IntRange(8, 17))
        )

        fun calculateStats(level: Int) : OrcParameters{
            val rng = Random.Default

            val parametersRanges = orcParametersRanges[level]!!
            return OrcParameters(
                rng.nextInt(parametersRanges.maxHitpoints),
                rng.nextInt(parametersRanges.attack),
                rng.nextInt(parametersRanges.defense),
                rng.nextInt(parametersRanges.visionRadius))
        }
    }

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
