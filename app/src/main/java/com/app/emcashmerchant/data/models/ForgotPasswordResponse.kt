package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class ForgotPasswordResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("otpExpirySeconds")
        val otpExpirySeconds: String,
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @SerializedName("referenceId")
        val referenceId: String
    )
}