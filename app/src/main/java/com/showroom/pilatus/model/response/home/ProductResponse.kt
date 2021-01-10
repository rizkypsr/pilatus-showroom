package com.showroom.pilatus.model.response.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductResponse(
    @Expose
    @SerializedName("data")
    val data: List<Data>
)