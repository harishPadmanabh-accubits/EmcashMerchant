package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName


data class RecieptRequest(
    @SerializedName("transactionId")
    val transactionId: String
)