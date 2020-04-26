package com.github.jroskopf.wfc.model.data

import com.google.gson.annotations.SerializedName

data class Neighbors(
        @SerializedName("neighbor")
        val neighbor: List<Neighbor?>?
)