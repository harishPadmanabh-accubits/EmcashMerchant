package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class GenerateQrCodeRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String
)