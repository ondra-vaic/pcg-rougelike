package cz.cuni.gamedev.nail123.roguelike

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.world.World
import cz.cuni.gamedev.nail123.roguelike.world.WorldBuilder
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class Game(val world: World, val player: Player) {

    companion object {
        fun create(worldSize: Size3D = GameConfig.WORLD_SIZE,
                   visibleSize: Size3D = GameConfig.VISIBLE_SIZE): Game {

            val player = Player()
            val world = WorldBuilder(worldSize)
                    .makeCaves()
                    .build(visibleSize)

            val res = world.addAtEmptyPosition(
                    player,
                    Position3D.create(0, 0, 1),
                    GameConfig.VISIBLE_SIZE.copy(zLength = 1)
            )

            if (!res) throw Exception("Error placing player!")
            return Game(world, player)
        }
    }
}