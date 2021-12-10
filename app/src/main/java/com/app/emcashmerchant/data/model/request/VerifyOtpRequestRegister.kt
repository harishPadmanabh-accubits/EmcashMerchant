package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName

data class VerifyOtpRequestRegister(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("referenceId")
    val referenceId: String
)