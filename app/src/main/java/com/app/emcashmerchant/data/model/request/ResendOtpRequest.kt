package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName

data class ResendOtpRequest(
    @SerializedName("referenceId")
    val referenceId: String
)