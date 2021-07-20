package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class CustomerContactResponse(
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
        val profileImage: Any,
        @SerializedName("roleId")
        val roleId: Int
    )
}