package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName

data class PinNumberVerifyResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("upload_document_token")
        val uploadDocumentToken: String?=null,
        @SerializedName("requestingAddInfo")
        val requestingAddInfo: Boolean,
        @SerializedName("userStatus")
        val userStatus: Int
    )
}