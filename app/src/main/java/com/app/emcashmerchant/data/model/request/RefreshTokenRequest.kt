package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class RefreshTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
)