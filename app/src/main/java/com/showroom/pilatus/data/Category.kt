package com.showroom.pilatus.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var name: String,
    var icon: String
) : Parcelable {
}