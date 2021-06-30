package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName

data class PinNumberVerifyRequest(
    @SerializedName("pin")
    val pin: Int
)