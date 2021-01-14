package com.showroom.pilatus.model.response.ongkir.city


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Status(

    @Expose
    @SerializedName("code")
    val code: Int,

    @Expose
    @SerializedName("description")
    val description: String
)