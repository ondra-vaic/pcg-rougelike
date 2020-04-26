package cz.cuni.gamedev.nail123.roguelike.tiles

import cz.cuni.gamedev.nail123.roguelike.world.Direction

private val all8 = Direction.eightDirections.sumBy { it.flag }
val wallTiling = Autotiling(
        // Default
        GameTiles.graphicalTile("Wall 20"),
        all8 to GameTiles.graphicalTile("Black"),
        all8 - Direction.NORTH_WEST.flag to GameTiles.graphicalTile("Wall thick N W"),
        all8 - Direction.NORTH_EAST.flag to GameTiles.graphicalTile("Wall thick N E"),
        all8 - Direction.SOUTH_WEST.flag to GameTiles.graphicalTile("Wall thick S W"),
        all8 - Direction.SOUTH_EAST.flag to GameTiles.graphicalTile("Wall thick S E"),
        Direction.NORTH + Direction.SOUTH to GameTiles.graphicalTile("Wall thick N S"),
        Direction.WEST + Direction.EAST to GameTiles.graphicalTile("Wall thick W E"),
        Direction.NORTH + Direction.EAST to GameTiles.graphicalTile("Wall thick N E"),
        Direction.NORTH + Direction.WEST to GameTiles.graphicalTile("Wall thick N W"),
        Direction.SOUTH + Direction.EAST to GameTiles.graphicalTile("Wall thick S E"),
        Direction.SOUTH + Direction.WEST to GameTiles.graphicalTile("Wall thick S W"),
        Direction.NORTH.flag to GameTiles.graphicalTile("Wall thick N S"),
        Direction.WEST.flag to GameTiles.graphicalTile("Wall thick E W"),
        Direction.EAST.flag to GameTiles.graphicalTile("Wall thick E W"),
        Direction.SOUTH.flag to GameTiles.graphicalTile("Wall thick N S")
)