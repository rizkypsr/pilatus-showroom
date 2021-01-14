package com.showroom.pilatus.model.response.ongkir.cost


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CostResponse(
    @Expose
    @SerializedName("rajaongkir")
    val rajaongkir: Rajaongkir
)