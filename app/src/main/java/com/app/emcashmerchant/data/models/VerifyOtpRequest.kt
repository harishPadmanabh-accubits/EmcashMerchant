package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("referenceId")
    val referenceId: String
)