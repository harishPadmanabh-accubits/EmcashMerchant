package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName

data class ResendOtpRequest(
    @SerializedName("referenceId")
    val referenceId: String
)