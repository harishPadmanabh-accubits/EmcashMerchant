package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class ResetPasswordRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("referenceId")
    val referenceId: String
)