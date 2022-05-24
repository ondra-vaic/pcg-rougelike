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

        val orcParametersRanges = mapOf(
            1 to OrcParametersRanges(
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0)),
            2 to OrcParametersRanges(
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0)),
            3 to OrcParametersRanges(
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0)),
            4 to OrcParametersRanges(
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0)),
            5 to OrcParametersRanges(
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0),
                IntRange(0, 0))
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
