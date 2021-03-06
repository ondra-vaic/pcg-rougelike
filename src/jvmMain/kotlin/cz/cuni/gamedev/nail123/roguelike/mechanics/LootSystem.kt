package cz.cuni.gamedev.nail123.roguelike.mechanics

import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Enemy
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Orc
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Rat
import cz.cuni.gamedev.nail123.roguelike.entities.items.*
import kotlin.random.Random
import kotlin.math.ln

object LootSystem {

    // Store rng for convenience
    val rng = Random.Default

    private fun levelStrengthCurve(num: Int, level: Int): Int{
        return (num * (ln(level.toDouble()) + 1)).toInt()
    }

    private fun levelStrengthCurve(num: Float, level: Int): Float{
        val numFloat = (num * (ln(level.toDouble()) + 1)).toFloat()
        return (numFloat * 100).toInt() / 100f
    }

    val sword = { level: Int ->
        SingleDrop {
            Sword(
                levelStrengthCurve(
                    rng.nextInt(3) + 2,
                    level
                )
            )
        }
    }
    val rareSword = { level: Int ->
        SingleDrop {
            Sword(
                levelStrengthCurve(
                    rng.nextInt(2) + 5,
                    level
                )
            )
        }
    }
    val veryRareSword = { level: Int ->
        SingleDrop {
            Sword(
                levelStrengthCurve(
                    rng.nextInt(10) + 1,
                    level
                )
            )
        }
    }

    val body = { level: Int ->
        SingleDrop {
            Body(
                levelStrengthCurve(
                    rng.nextInt(3) + 2,
                    level
                ),
                levelStrengthCurve(
                    rng.nextInt(4) + 2,
                    level
                )
            )
        }
    }
    val rareBody = { level: Int ->
        SingleDrop {
            Body(
                levelStrengthCurve(
                    rng.nextInt(5) + 3, level
                ),
                levelStrengthCurve(
                    rng.nextInt(6) + 3, level
                )
            )
        }
    }
    val veryRareBody = { level: Int ->
        SingleDrop {
            Body(
                levelStrengthCurve(
                    rng.nextInt(10) + 1,
                    level
                ),
                levelStrengthCurve(rng.nextInt(13) + 3,
                    level
                )
            )
        }
    }

    val ring = { level: Int ->
        SingleDrop {
            Ring(
                levelStrengthCurve(
                    rng.nextInt(1),
                    level
                ),
                levelStrengthCurve(
                    rng.nextInt(1) + 1,
                    level
                ),
                levelStrengthCurve(
                    rng.nextFloat() * 0.05f + 0.02f,
                    level
                ),
            )
        }
    }
    val rareRing = { level: Int ->
        SingleDrop {
            Ring(
                levelStrengthCurve(
                    rng.nextInt(1) + 1,
                    level
                ),
                levelStrengthCurve(
                    rng.nextInt(3) + 2,
                    level
                ),
                levelStrengthCurve(
                    rng.nextFloat() * 0.1f + 0.04f,
                    level
                ),
            )
        }
    }
    val veryRareRing = { level: Int ->
        SingleDrop {
            Ring(
                levelStrengthCurve(
                    rng.nextInt(4),
                    level
                ),
                levelStrengthCurve(
                    rng.nextInt(10),
                    level
                ),
                levelStrengthCurve(
                    rng.nextFloat() * 0.15f + 0.01f,
                    level
                ),
            )
        }
    }

    val shield = { level: Int -> SingleDrop {
            Shield(
                levelStrengthCurve(
                    rng.nextInt(1) + 1,
                    level
                )
            )
        }
    }
    val rareShield = { level: Int ->
        SingleDrop {
            Shield(
                levelStrengthCurve(
                    rng.nextInt(3) + 1,
                    level
                )
            )
        }
    }
    val veryRareShield = { level: Int ->
        SingleDrop {
            Shield(
                levelStrengthCurve(
                    rng.nextInt(5),
                    level
                )
            )
        }
    }

    val enemyDrops = mapOf(
        Rat::class to mapOf(
            1 to TreasureClass(1, listOf(
                8 to NoDrop,
                5 to ring(1),
                3 to rareRing(1),
                1 to veryRareRing(1))
            ),
            2 to TreasureClass(1, listOf(
                8 to NoDrop,
                5 to ring(2),
                3 to rareRing(2),
                1 to veryRareRing(2))
            ),
            3 to TreasureClass(1, listOf(
                10 to NoDrop,
                5 to ring(3),
                3 to rareRing(3),
                1 to veryRareRing(3))
            ),
            4 to TreasureClass(1, listOf(
                15 to NoDrop,
                4 to ring(4),
                2 to rareRing(4),
                1 to veryRareRing(4))
            ),
            5 to TreasureClass(1, listOf(
                16 to NoDrop,
                2 to ring(5),
                3 to rareRing(5),
                2 to veryRareRing(5))
            )
        ),

        Orc::class to mapOf(
            1 to TreasureClass(1, listOf(
                10 to NoDrop,
                5 to sword(1),
                2 to rareSword(1),
                1 to veryRareSword(1))
            ),
            2 to TreasureClass(1, listOf(
                20 to NoDrop,
                4 to body(2),
                2 to rareBody(2),
                4 to sword(2),
                2 to veryRareSword(2))
            ),
            3 to TreasureClass(1, listOf(
                24 to NoDrop,
                4 to shield(3),
                2 to rareShield(3),
                4 to sword(3),
                2 to rareSword(3),
                1 to veryRareSword(3))
            ),
            4 to TreasureClass(1, listOf(
                30 to NoDrop,
                4 to shield(4),
                2 to rareShield(4),
                1 to veryRareShield(4),
                4 to body(4),
                2 to rareBody(4),
                1 to veryRareBody(4),
                4 to sword(4),
                2 to rareSword(4),
                1 to veryRareSword(4))
            ),
            5 to TreasureClass(1, listOf(
                35 to NoDrop,
                5 to shield(5),
                3 to rareShield(5),
                2 to veryRareShield(5),
                5 to body(5),
                3 to rareBody(5),
                2 to veryRareBody(5),
                5 to sword(5),
                3 to rareSword(5),
                2 to veryRareSword(5))
            )
        ),
    )

    interface ItemDrop {
        fun getDrops(): List<Item>
    }

    object NoDrop: ItemDrop {
        override fun getDrops() = listOf<Item>()
    }

    open class SingleDrop(open val instantiator: () -> Item): ItemDrop {
        override fun getDrops() = listOf(instantiator())
    }

    fun onDeath(enemy: Enemy) {
        val drops = enemyDrops[enemy::class]?.get(enemy.level)?.getDrops() ?: return
        for (item in drops) {
            enemy.area[enemy.position]?.entities?.add(item)
        }
    }

    class TreasureClass(val numDrops: Int, val possibleDrops: List<Pair<Int, ItemDrop>>): ItemDrop {
        val totalProb = possibleDrops.map { it.first }.sum()

        override fun getDrops(): List<Item> {
            val drops = ArrayList<Item>()
            repeat(numDrops) {
                drops.addAll(pickDrop().getDrops())
            }
            return drops
        }

        private fun pickDrop(): ItemDrop {
            var randNumber = Random.Default.nextInt(totalProb)
            for (drop in possibleDrops) {
                randNumber -= drop.first
                if (randNumber < 0) return drop.second
            }
            // Never happens, but we need to place something here anyway
            return possibleDrops.last().second
        }
    }
}
