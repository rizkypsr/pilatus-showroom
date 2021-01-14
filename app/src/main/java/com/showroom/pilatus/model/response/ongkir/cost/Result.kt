package com.showroom.pilatus.model.response.ongkir.cost


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @Expose
    @SerializedName("code")
    val code: String,

    @Expose
    @SerializedName("costs")
    val costs: List<Cost>,

    @Expose
    @SerializedName("name")
    val name: String
)