package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class PaymentRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("userId")
    val userId: Int
)