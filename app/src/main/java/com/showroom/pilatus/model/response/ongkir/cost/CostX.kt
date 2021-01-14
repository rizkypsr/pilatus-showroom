package com.showroom.pilatus.model.response.ongkir.cost


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CostX(
    @Expose
    @SerializedName("etd")
    val etd: String,

    @Expose
    @SerializedName("note")
    val note: String,

    @Expose
    @SerializedName("value")
    val value: Int
) : Parcelable