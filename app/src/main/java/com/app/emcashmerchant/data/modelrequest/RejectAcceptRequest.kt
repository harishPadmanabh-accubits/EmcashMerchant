package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class RejectAcceptRequest(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("pin")
    val pin: Int,
    @SerializedName("transactionId")
    val transactionId: String
)