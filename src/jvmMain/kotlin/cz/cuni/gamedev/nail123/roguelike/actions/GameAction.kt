package cz.cuni.gamedev.nail123.roguelike.actions

import cz.cuni.gamedev.nail123.roguelike.world.World

abstract class GameAction {
    abstract fun perform(world: World)
}