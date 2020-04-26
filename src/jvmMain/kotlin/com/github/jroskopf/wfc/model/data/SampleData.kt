package com.github.jroskopf.wfc.model.data

import com.google.gson.annotations.SerializedName

data class SampleData(
        @SerializedName("set")
        val `set`: Set?
)