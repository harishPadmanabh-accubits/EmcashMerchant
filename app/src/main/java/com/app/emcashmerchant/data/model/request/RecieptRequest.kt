package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class RecieptRequest(
    @SerializedName("transactionId")
    val transactionId: String
)