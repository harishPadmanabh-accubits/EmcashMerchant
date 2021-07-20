package com.app.emcashmerchant.data.models

import com.google.gson.annotations.SerializedName


data class PaymentReceiptResponse(
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
        @SerializedName("transferUserInfo")
        val beneficiary: Beneficiary,
        @SerializedName("beneficiaryId")
        val beneficiaryId: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("expiresIn")
        val expiresIn: Any,
        @SerializedName("iban")
        val iban: Any,
        @SerializedName("id")
        val id: String,
        @SerializedName("handShakingStatus")
        val handShakingStatus: Boolean,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("method")
        val method: Int,
        @SerializedName("paymentConfirmedAt")
        val paymentConfirmedAt: String,
        @SerializedName("referenceId")
        val referenceId: String,
        @SerializedName("referenceInfo")
        val referenceInfo: String,
        @SerializedName("remitter")
        val remitter: Remitter,
        @SerializedName("remitterId")
        val remitterId: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("walletTransactionInfo")
        val walletTransactions: WalletTransactions
    ) {
        data class Beneficiary(
            @SerializedName("email")
            val email: String,
            @SerializedName("userId")
            val userid: String,
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

        data class Remitter(
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

        data class WalletTransactions(
            @SerializedName("balance")
            val balance: Int,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("mode")
            val mode: Int,
            @SerializedName("transactionId")
            val transactionId: String,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("userId")
            val userId: String,
            @SerializedName("walletId")
            val walletId: String
        )
    }
}