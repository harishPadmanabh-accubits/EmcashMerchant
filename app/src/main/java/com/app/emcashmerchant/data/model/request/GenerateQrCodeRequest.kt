package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class GenerateQrCodeRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String
)