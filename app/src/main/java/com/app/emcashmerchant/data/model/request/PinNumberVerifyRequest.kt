package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName

data class PinNumberVerifyRequest(
    @SerializedName("pin")
    val pin: Int
)