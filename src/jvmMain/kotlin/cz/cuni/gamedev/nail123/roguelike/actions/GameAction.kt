package cz.cuni.gamedev.nail123.roguelike.actions

import cz.cuni.gamedev.nail123.roguelike.world.Area

abstract class GameAction {
    /**
     * Attempts performing the action. Returns whether the player was able to perform the action, otherwise time won't
     * move.
     */
    abstract fun tryPerform(area: Area): Boolean
}