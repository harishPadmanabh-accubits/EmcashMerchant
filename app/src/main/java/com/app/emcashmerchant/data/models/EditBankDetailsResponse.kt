package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName



data class EditBankDetailsResponse(
    @SerializedName("data")
    val `data`: Boolean,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)