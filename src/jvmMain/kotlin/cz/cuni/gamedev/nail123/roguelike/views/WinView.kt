package cz.cuni.gamedev.nail123.roguelike.views

import org.hexworks.zircon.api.grid.TileGrid

class WinView(tileGrid: TileGrid): LoseView(tileGrid) {
    override val msg = "You won!"
}