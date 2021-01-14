package com.showroom.pilatus.model.response.ongkir.cost


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Query(
    @Expose
    @SerializedName("courier")
    val courier: String,

    @Expose
    @SerializedName("destination")
    val destination: String,

    @Expose
    @SerializedName("origin")
    val origin: String,

    @Expose
    @SerializedName("weight")
    val weight: Int
)