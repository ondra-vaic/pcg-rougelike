package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.world.builders.AreaBuilder
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea

/**
 * This class looks like a BaseGameArea on the outside, but it is actually just a wrapper around a (switchable) Area.
 * It is an adapter for:
 *  - drawing - you can transition from one area to another within the same component
 *  - world - you can (if you want) only update entities in the current area
 */
class AreaSwitcher(visibleSize: Size3D, actualSize: Size3D): BaseGameArea<Tile, GameBlock>(
        visibleSize, actualSize
    ), IArea {

    var innerArea = AreaBuilder.empty()
        protected set

    override val size: Size3D
        get() = innerArea.size
    override val entities: List<GameEntity>
        get() = innerArea.entities

    val player
        get() = innerArea.player

    override fun get(position: Position3D) = innerArea.get(position)
    override fun addEntity(entity: GameEntity, position: Position3D) = innerArea.addEntity(entity, position)
    override fun removeEntity(entity: GameEntity) = innerArea.removeEntity(entity)

    fun switchTo(area: Area) {
        innerArea = area
        state = state.copy(blocks = area.state.blocks)
    }
}