package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class RefreshTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
)