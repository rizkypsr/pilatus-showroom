package com.showroom.pilatus.model.response.home


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryResponse(
    @Expose
    @SerializedName("created_at")
    val createdAt: Long,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("picturePath")
    val picturePath: String,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long
) : Parcelable