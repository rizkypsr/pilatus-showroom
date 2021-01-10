package com.showroom.pilatus.model.request

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class RegisterRequest(
    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("email")
    val email: String,

    @Expose
    @SerializedName("password")
    val password: String,

    @Expose
    @SerializedName("password_confirmation")
    val password_confirmation: String,

    @Expose
    @SerializedName("address")
    val address: String,

    @Expose
    @SerializedName("city")
    val city: String,

    @Expose
    @SerializedName("houseNumber")
    val houseNumber: String,

    @Expose
    @SerializedName("phoneNumber")
    val phoneNumber: String,

    @Expose
    @SerializedName("filePath")
    val filePath: Uri? = null
) : Parcelable