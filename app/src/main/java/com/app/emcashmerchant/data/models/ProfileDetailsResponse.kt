package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class ProfileDetailsResponse(
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
        @SerializedName("isBankAccoutExists")
        val isBankAccoutExists: Boolean,
        @SerializedName("address")
        val address: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("fcmToken")
        val fcmToken: Any,
        @SerializedName("guid")
        val guid: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("isRegistrationCompleted")
        val isRegistrationCompleted: Boolean,
        @SerializedName("merchant")
        val merchant: Merchant,
        @SerializedName("merchantTradeInfo")
        val merchantTradeInfo: MerchantTradeInfo,
        @SerializedName("name")
        val name: String?,
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @SerializedName("profileImage")
        val profileImage: String?,
        @SerializedName("roleId")
        val roleId: Int,
        @SerializedName("status")
        val status: Int,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("zipCode")
        val zipCode: String
    ) {
        data class Merchant(
            @SerializedName("accountRejectReason")
            val accountRejectReason: Any,
            @SerializedName("accountRejectReasonDetails")
            val accountRejectReasonDetails: Any,
            @SerializedName("accountStatusUpdatedAt")
            val accountStatusUpdatedAt: String,
            @SerializedName("bankDetailsDoc")
            val bankDetailsDoc: String,
            @SerializedName("commercialRegistrationDoc")
            val commercialRegistrationDoc: String,
            @SerializedName("contactPersonName")
            val contactPersonName: String,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("kycStatus")
            val kycStatus: Boolean,
            @SerializedName("registeredNameOfBusiness")
            val registeredNameOfBusiness: String,
            @SerializedName("requestingAddInfo")
            val requestingAddInfo: Boolean,
            @SerializedName("requestingDocDetails")
            val requestingDocDetails: Any,
            @SerializedName("requestingDocType")
            val requestingDocType: Any,
            @SerializedName("serviceDescription")
            val serviceDescription: String,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("userId")
            val userId: String
        )

        data class MerchantTradeInfo(
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("tradeLicenseDoc")
            val tradeLicenseDoc: String,
            @SerializedName("tradeLicenseExpirationDate")
            val tradeLicenseExpirationDate: Any,
            @SerializedName("tradeLicenseIssuingAuthority")
            val tradeLicenseIssuingAuthority: String,
            @SerializedName("tradeLicenseNumber")
            val tradeLicenseNumber: String,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("userId")
            val userId: String
        )
    }
}