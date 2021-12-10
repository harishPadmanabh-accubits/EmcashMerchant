package com.app.emcashmerchant.data.model.response


import com.google.gson.annotations.SerializedName


data class PayerAuthenticatorResponse(
    @SerializedName("data")
    val `data`: Boolean,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean

)