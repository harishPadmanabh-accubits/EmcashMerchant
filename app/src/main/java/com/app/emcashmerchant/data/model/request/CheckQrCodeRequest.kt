package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class CheckQrCodeRequest(
    @SerializedName("referenceId")
    val referenceId: String
)