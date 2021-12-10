package com.app.emcashmerchant.data.model.response


import com.google.gson.annotations.SerializedName


data class TermsConditionsResponse(
    @SerializedName("data")
    val data: String,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)