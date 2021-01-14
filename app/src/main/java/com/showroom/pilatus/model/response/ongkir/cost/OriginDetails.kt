package com.showroom.pilatus.model.response.ongkir.cost


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OriginDetails(

    @Expose
    @SerializedName("city_id")
    val cityId: String,

    @Expose
    @SerializedName("city_name")
    val cityName: String,

    @Expose
    @SerializedName("postal_code")
    val postalCode: String,

    @Expose
    @SerializedName("province")
    val province: String,

    @Expose
    @SerializedName("province_id")
    val provinceId: String,

    @Expose
    @SerializedName("type")
    val type: String
)