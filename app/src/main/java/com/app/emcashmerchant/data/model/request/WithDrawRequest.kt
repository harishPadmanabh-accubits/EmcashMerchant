package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class WithDrawRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("iban")
    val iban: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)