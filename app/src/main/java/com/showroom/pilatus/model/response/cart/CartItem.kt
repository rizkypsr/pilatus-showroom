package com.showroom.pilatus.model.response.cart

import android.os.Parcelable
import com.showroom.pilatus.model.response.home.Data
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    var product: Data,
    var quantity: Int = 0
) : Parcelable