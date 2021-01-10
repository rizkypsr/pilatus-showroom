package com.showroom.pilatus.model.profile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("current_team_id")
    val currentTeamId: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("houseNumber")
    val houseNumber: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("picturePath")
    val picturePath: Any,
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String,
    @SerializedName("roles")
    val roles: String,
    @SerializedName("updated_at")
    val updatedAt: Long
)