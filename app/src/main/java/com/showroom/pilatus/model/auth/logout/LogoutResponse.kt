package com.showroom.pilatus.model.auth.logout


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoutResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("data")
    val data: Boolean,
) : Parcelable