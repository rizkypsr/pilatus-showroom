package com.showroom.pilatus.model.response.ongkir.cost


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cost(
    @Expose
    @SerializedName("cost")
    val cost: List<CostX>,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("service")
    val service: String
) : Parcelable