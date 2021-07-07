package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class FinalSignupRequest(
    @SerializedName("referenceId")
    val referenceId: String
)