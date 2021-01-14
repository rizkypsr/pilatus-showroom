package com.showroom.pilatus.model.response.ongkir.city


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rajaongkir(
    @Expose
    @SerializedName("query")
    val query: List<Any>,

    @Expose
    @SerializedName("results")
    val results: List<Result>,

    @Expose
    @SerializedName("status")
    val status: Status
)