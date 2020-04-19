package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.blocks.Floor
import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.extensions.floorNeighbors
import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class WorldBuilder(val worldSize: Size3D): IWorld {
    val width
        get() = worldSize.xLength
    val height
        get() = worldSize.zLength
    val allPositions
        get() = worldSize.fetchPositions()

    override var blocks = mutableMapOf<Position3D, GameBlock>()
    override val entities = mutableListOf<GameEntity>()

    override fun get(position: Position3D) = blocks[position]

    var player = Player()

    fun makeCaves() = randomizeTiles().smoothen(8)
    fun addPlayer() = apply {
        addAtEmptyPosition(
                player,
                Position3D.create(0, 0, 0),
                GameConfig.VISIBLE_SIZE
        )
        println("Player position is ${player.location}")
    }

    fun build(visibleSize: Size3D): World {
        val world = World(blocks.toPersistentMap(), visibleSize, worldSize, player)
        for (entity in entities) {
            world.entities.add(entity)
            entity.world = world
        }
        return world
    }

    protected fun randomizeTiles() = apply {
        allPositions.forEach { pos ->
            blocks[pos] = if (Math.random() < 0.5) Floor() else Wall()
        }
    }

    protected fun smoothen(iterations: Int) = apply {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()

        repeat(iterations) {
            allPositions.forEach { pos ->
                val neighbors = pos.floorNeighbors()
                val walls = neighbors.filter { blocks[it]?.javaClass == Wall::class.java }.count()
                val floors = neighbors.count() - walls

                newBlocks[pos] = if (floors >= walls) Floor() else Wall()
            }
            blocks = newBlocks
        }
    }
}