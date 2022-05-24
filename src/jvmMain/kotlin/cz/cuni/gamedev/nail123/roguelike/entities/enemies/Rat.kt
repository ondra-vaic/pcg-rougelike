package cz.cuni.gamedev.nail123.roguelike.entities.enemies

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasSmell
import cz.cuni.gamedev.nail123.roguelike.entities.items.Sword
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.mechanics.goBlindlyTowards
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Rat(override val maxHitpoints: Int, override var attack: Int, override var defense: Int, override val smellingRadius: Int): Enemy(GameTiles.RAT), HasSmell {
    override val blocksMovement = true
    override val blocksVision = false
    override var hitpoints = maxHitpoints

    override fun update() {
        if (Pathfinding.chebyshev(position, area.player.position) <= smellingRadius) {
            goBlindlyTowards(area.player.position)
        }
    }

//    override fun die() {
//        super.die()
//    }
}