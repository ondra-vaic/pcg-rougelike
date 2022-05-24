package cz.cuni.gamedev.nail123.roguelike.entities.enemies

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasSmell
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.mechanics.goBlindlyTowards
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import kotlin.random.Random
import kotlin.random.nextInt

data class RatParameters(val maxHitpoints: Int, val attack: Int, val defense: Int, val smellRadius: Int)
data class RatParametersRanges(val maxHitpoints: IntRange, val attack: IntRange, val defense: IntRange, val smellRadius: IntRange)

class Rat(override val level: Int): Enemy(level, GameTiles.RAT), HasSmell {
    override val blocksMovement = true
    override val blocksVision = false
    override var hitpoints = 0

    override val maxHitpoints: Int
    override val smellingRadius: Int
    override var attack: Int = 0
    override var defense: Int = 0

    init {
        val stats = calculateStats(level)
        maxHitpoints = stats.maxHitpoints
        attack = stats.attack
        defense = stats.defense
        smellingRadius = stats.smellRadius
        hitpoints = maxHitpoints
    }

    companion object {

        private val ratParametersRanges = mapOf(
            1 to RatParametersRanges(
                IntRange(3, 5),
                IntRange(4, 6),
                IntRange(2, 4),
                IntRange(5, 8)),
            2 to RatParametersRanges(
                IntRange(5, 7),
                IntRange(6, 10),
                IntRange(3, 5),
                IntRange(5, 11)),
            3 to RatParametersRanges(
                IntRange(7, 11),
                IntRange(10, 16),
                IntRange(3, 7),
                IntRange(5, 14)),
            4 to RatParametersRanges(
                IntRange(11, 14),
                IntRange(14, 20),
                IntRange(4, 8),
                IntRange(8, 14)),
            5 to RatParametersRanges(
                IntRange(14, 18),
                IntRange(18, 25),
                IntRange(4, 9),
                IntRange(8, 17))
        )

        fun calculateStats(level: Int) : RatParameters{
            val rng = Random.Default
            val parametersRanges = ratParametersRanges[level]!!
            return RatParameters(
                rng.nextInt(parametersRanges.maxHitpoints),
                rng.nextInt(parametersRanges.attack),
                rng.nextInt(parametersRanges.defense),
                rng.nextInt(parametersRanges.smellRadius))
        }
    }

    override fun update() {
        if (Pathfinding.chebyshev(position, area.player.position) <= smellingRadius) {
            goBlindlyTowards(area.player.position)
        }
    }
}