package cz.cuni.gamedev.nail123.roguelike.world.builders

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.world.IArea
import cz.cuni.gamedev.nail123.roguelike.world.Area
import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

abstract class AreaBuilder(override val size: Size3D, val visibleSize: Size3D = GameConfig.VISIBLE_SIZE): IArea {
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

    fun build(): Area {
        create()

        val area = Area(blocks.toPersistentMap(), visibleSize, size, player)
        for (entity in entities) {
            entity.area = area
        }
        return area
    }

    protected abstract fun create(): AreaBuilder

    companion object {
        class EmptyBuilder(size: Size3D): AreaBuilder(size) {
            override fun create() = apply {
                addEntity(player, Position3D.create(0, 0, 0))
            }
        }
        fun empty() = EmptyBuilder(GameConfig.AREA_SIZE).build()
    }
}