package com.showroom.pilatus.model.response.transaction


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionData(
    @Expose
    @SerializedName("courier_price")
    val courierPrice: Int,

    @Expose
    @SerializedName("courier_type")
    val courierType: String,

    @Expose
    @SerializedName("created_at")
    val createdAt: Long,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("payment")
    val payment: String,

    @Expose
    @SerializedName("product")
    val product: Product,

    @Expose
    @SerializedName("product_id")
    val productId: Int,

    @Expose
    @SerializedName("quantity")
    val quantity: Int,

    @Expose
    @SerializedName("status")
    val status: String,

    @Expose
    @SerializedName("total")
    val total: Int,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long,

    @Expose
    @SerializedName("user")
    val user: User,

    @Expose
    @SerializedName("user_id")
    val userId: Int
) : Parcelable