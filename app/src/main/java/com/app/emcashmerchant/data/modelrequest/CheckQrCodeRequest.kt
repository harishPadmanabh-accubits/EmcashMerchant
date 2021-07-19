package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class CheckQrCodeRequest(
    @SerializedName("referenceId")
    val referenceId: String
)