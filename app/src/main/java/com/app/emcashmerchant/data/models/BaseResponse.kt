package com.app.emcashmerchant.data.models

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)  {
}