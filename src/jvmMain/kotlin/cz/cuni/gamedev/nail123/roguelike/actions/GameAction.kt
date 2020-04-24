package cz.cuni.gamedev.nail123.roguelike.actions

import cz.cuni.gamedev.nail123.roguelike.world.Area

abstract class GameAction {
    abstract fun perform(area: Area)
}