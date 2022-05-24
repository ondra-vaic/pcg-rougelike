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
        return (num * (ln(level.toDouble()) + 1)).toFloat()
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
                    rng.nextInt(1) + 1,
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
                    rng.nextInt(3) + 1,
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
                    rng.nextInt(9),
                    level
                ),
                levelStrengthCurve(
                    rng.nextInt(10),
                    level
                ),
                levelStrengthCurve(
                    rng.nextFloat() * 0.25f + 0.01f,
                    level
                ),
            )
        }
    }

    val shield = { level: Int -> SingleDrop {
            Shield(
                levelStrengthCurve(
                    rng.nextInt(5) + 2,
                    level
                )
            )
        }
    }
    val rareShield = { level: Int ->
        SingleDrop {
            Shield(
                levelStrengthCurve(
                    rng.nextInt(10) + 1,
                    level
                )
            )
        }
    }
    val veryRareShield = { level: Int ->
        SingleDrop {
            Shield(
                levelStrengthCurve(
                    rng.nextInt(25),
                    level
                )
            )
        }
    }

    val enemyDrops = mapOf(
        Rat::class to mapOf(
            1 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to veryRareShield(1))
            ),
            2 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(2))
            ),
            3 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(3))
            ),
            4 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(4))
            ),
            5 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(5))
            )
        ),
        Orc::class to mapOf(
            1 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(1))
            ),
            2 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(2))
            ),
            3 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(3))
            ),
            4 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(4))
            ),
            5 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword(5))
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
