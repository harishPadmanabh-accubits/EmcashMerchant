package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class TopUpRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)