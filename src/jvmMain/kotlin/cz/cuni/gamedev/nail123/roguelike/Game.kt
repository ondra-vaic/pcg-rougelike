package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.world.World
import cz.cuni.gamedev.nail123.roguelike.world.WorldBuilder
import org.hexworks.zircon.api.data.Size3D

class Game(val world: World) {
    companion object {
        fun create(worldSize: Size3D = GameConfig.WORLD_SIZE,
                   visibleSize: Size3D = GameConfig.VISIBLE_SIZE) = Game(
                WorldBuilder(worldSize)
                        .makeCaves()
                        .build(visibleSize)
        )
    }
}