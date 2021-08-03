package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class IntiateContactPaymentRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("userId")
    val userId: Int
)