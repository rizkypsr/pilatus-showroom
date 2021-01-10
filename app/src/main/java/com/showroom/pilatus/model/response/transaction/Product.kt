package com.showroom.pilatus.model.response.transaction


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @Expose
    @SerializedName("category_id")
    val categoryId: Int,

    @Expose
    @SerializedName("created_at")
    val createdAt: Long,

    @Expose
    @SerializedName("description")
    val description: String,

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
    @SerializedName("price")
    val price: Int,

    @Expose
    @SerializedName("stock")
    val stock: Int,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long,

    @Expose
    @SerializedName("weight")
    val weight: Int
) : Parcelable