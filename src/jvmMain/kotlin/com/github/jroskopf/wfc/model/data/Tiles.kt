package com.github.jroskopf.wfc.model.data

import com.google.gson.annotations.SerializedName

data class Tiles(
        @SerializedName("tile")
        val tile: List<Tile?>?
)