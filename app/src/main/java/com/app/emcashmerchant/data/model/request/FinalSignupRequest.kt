package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class FinalSignupRequest(
    @SerializedName("referenceId")
    val referenceId: String,

    @SerializedName("fcmToken")
    val fcmToken: String
)