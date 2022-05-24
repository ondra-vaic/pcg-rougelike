package cz.cuni.gamedev.nail123.roguelike.mechanics

import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Enemy
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Orc
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Rat
import cz.cuni.gamedev.nail123.roguelike.entities.items.Item
import cz.cuni.gamedev.nail123.roguelike.entities.items.Sword
import kotlin.random.Random

object LootSystem {

    // Store rng for convenience
    val rng = Random.Default

    // Sword with power 2-4
    val sword = SingleDrop { Sword(rng.nextInt(3) + 2) }
    // Sword with power 5-6
    val rareSword = SingleDrop { Sword(rng.nextInt(2) + 5) }

    val enemyDrops = mapOf(
        Rat::class to mapOf(
            1 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            2 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            3 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            4 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            5 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            )
        ),
        Orc::class to mapOf(
            1 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            2 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            3 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            4 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            ),
            5 to TreasureClass(1, listOf(
                2 to NoDrop,
                1 to sword)
            )
        ),
    )

    interface ItemDrop {
        fun getDrops(): List<Item>
    }

    object NoDrop: ItemDrop {
        override fun getDrops() = listOf<Item>()
    }

    class SingleDrop(val instantiator: () -> Item): ItemDrop {
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
