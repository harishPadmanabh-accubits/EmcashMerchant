package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class ReUploadUserDeatilsResponse(
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
        @SerializedName("merchantDocs")
        val merchantDocs: MerchantDocs,
        @SerializedName("user")
        val user: User
    ) {
        data class MerchantDocs(
            @SerializedName("bankDetailsDoc")
            val bankDetailsDoc: Any,
            @SerializedName("commercialRegistrationDoc")
            val commercialRegistrationDoc: Any,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("isSignUpDocuments")
            val isSignUpDocuments: Boolean,
            @SerializedName("requestingDocDetails")
            val requestingDocDetails: String,
            @SerializedName("requestingDocType")
            val requestingDocType: String,
            @SerializedName("tradeLicenseDoc")
            val tradeLicenseDoc: Any,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("userId")
            val userId: String
        )

        data class User(
            @SerializedName("email")
            val email: String,
            @SerializedName("id")
            val id: String
        )
    }
}