package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class TransferAmountRequest(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("pin")
    val pin: Int,
    @SerializedName("referenceId")
    val referenceId: String
)