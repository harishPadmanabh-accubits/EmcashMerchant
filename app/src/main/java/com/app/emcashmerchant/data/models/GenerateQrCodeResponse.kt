package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class GenerateQrCodeResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("qrCode")
        val qrCode: String,
        @SerializedName("referenceId")
        val referenceId: String
    )
}