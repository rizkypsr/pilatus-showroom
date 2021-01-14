package com.showroom.pilatus.model.response.ongkir.city


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @Expose
    @SerializedName("rajaongkir")
    val rajaongkir: Rajaongkir
)