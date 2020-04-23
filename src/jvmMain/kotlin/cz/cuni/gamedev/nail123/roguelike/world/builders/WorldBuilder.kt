package cz.cuni.gamedev.nail123.roguelike.world.builders

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.world.IWorld
import cz.cuni.gamedev.nail123.roguelike.world.World
import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

abstract class WorldBuilder(override val worldSize: Size3D): IWorld {
    override var blocks = mutableMapOf<Position3D, GameBlock>()
    override val entities = mutableListOf<GameEntity>()

    override fun get(position: Position3D) = blocks[position]

    override fun addEntity(entity: GameEntity, position: Position3D) {
        blocks[position]?.addEntity(entity)
        entities.add(entity)
        entity.position = position
    }
    override fun removeEntity(entity: GameEntity) {
        blocks[entity.position]?.removeEntity(entity)
        entities.remove(entity)
    }

    var player = Player()

    fun build(visibleSize: Size3D): World {
        val world = World(blocks.toPersistentMap(), visibleSize, worldSize, player)
        for (entity in entities) {
            entity.world = world
        }
        return world
    }

    abstract fun generate(): WorldBuilder
}