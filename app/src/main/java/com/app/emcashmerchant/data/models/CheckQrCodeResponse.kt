package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class CheckQrCodeResponse(
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
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @SerializedName("ppp")
        val ppp: Int,
        @SerializedName("profileImage")
        val profileImage: String,
        @SerializedName("roleId")
        val roleId: Int
    )
}