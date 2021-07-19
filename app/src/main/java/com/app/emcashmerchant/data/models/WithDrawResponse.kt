package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class WithDrawResponse(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)