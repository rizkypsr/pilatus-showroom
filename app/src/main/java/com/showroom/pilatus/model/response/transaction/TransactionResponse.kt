package com.showroom.pilatus.model.response.transaction


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransactionResponse(
    @Expose
    @SerializedName("data")
    val data: List<TransactionResponseItem>
)