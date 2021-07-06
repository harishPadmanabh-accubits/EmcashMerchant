package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName

data class LoginVerifyOtpResponse(
    @SerializedName("data")
    val `data`: Data,
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
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @SerializedName("tokens")
        val tokens: Tokens
    ) {
        data class Tokens(
            @SerializedName("accessToken")
            val accessToken: String,
            @SerializedName("refreshToken")
            val refreshToken: String
        )
    }
}