package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class WalletResponse(
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
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("userId")
        val userId: String,
        @SerializedName("walletAddress")
        val walletAddress: String,
        @SerializedName("notificationCount")
        val notificationCount : Int,
        @SerializedName("profileImage")
        val profileImage : String

    )
}