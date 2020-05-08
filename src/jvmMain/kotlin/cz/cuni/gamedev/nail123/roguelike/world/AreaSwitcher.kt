package cz.cuni.gamedev.nail123.roguelike.world

import cz.cuni.gamedev.nail123.roguelike.blocks.GameBlock
import cz.cuni.gamedev.nail123.roguelike.entities.GameEntity
import cz.cuni.gamedev.nail123.roguelike.world.builders.EmptyAreaBuilder
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

    var innerArea = EmptyAreaBuilder().build()
        protected set

    override val size: Size3D
        get() = innerArea.size
    val player
        get() = innerArea.player
    override val blocks: Map<Position3D, GameBlock>
        get() = innerArea.blocks

    override operator fun get(position: Position3D) = innerArea[position]
    override fun addEntity(entity: GameEntity, position: Position3D) = innerArea.addEntity(entity, position)
    override fun removeEntity(entity: GameEntity) = innerArea.removeEntity(entity)

    override fun setBlockAt(position: Position3D, block: GameBlock) = innerArea.setBlockAt(position, block)
    override fun fetchBlockAt(position: Position3D) = innerArea.fetchBlockAt(position)

    fun switchTo(area: Area) {
        innerArea = area
        state = state.copy(blocks = area.state.blocks)
    }
}