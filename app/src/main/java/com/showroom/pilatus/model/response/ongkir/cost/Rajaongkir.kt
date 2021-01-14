package com.showroom.pilatus.model.response.ongkir.cost


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rajaongkir(

    @Expose
    @SerializedName("destination_details")
    val destinationDetails: DestinationDetails,

    @Expose
    @SerializedName("origin_details")
    val originDetails: OriginDetails,

    @Expose
    @SerializedName("query")
    val query: Query,

    @Expose
    @SerializedName("results")
    val results: List<Result>,

    @Expose
    @SerializedName("status")
    val status: Status
)