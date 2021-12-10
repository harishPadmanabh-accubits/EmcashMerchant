package com.app.emcashmerchant.data.model.response


import com.google.gson.annotations.SerializedName


data class GroupedWalletTransactionResponse(
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
        @SerializedName("count")
        val count: Int,
        @SerializedName("limit")
        val limit: Int,
        @SerializedName("page")
        val page: Int,
        @SerializedName("rows")
        val rows: List<Row>,
        @SerializedName("totalPages")
        val totalPages: Int,
        @SerializedName("wallet")
        val wallet: Wallet
    ) {
        data class Row(
            @SerializedName("key")
            var key: String,
            @SerializedName("transactions")
            val transactions: List<Transaction>
        ) {
            data class Transaction(
                @SerializedName("balance")
                val balance: Int,
                @SerializedName("beneficiary")
                val beneficiary: Beneficiary,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("id")
                val id: String,
                @SerializedName("mode")
                val mode: Int,
                @SerializedName("remitter")
                val remitter: Remitter,
                @SerializedName("transactionId")
                val transactionId: String,
                @SerializedName("transactionInfo")
                val transactionInfo: TransactionInfo,
                @SerializedName("updatedAt")
                val updatedAt: String,
                @SerializedName("userId")
                val userId: Int,
                @SerializedName("walletId")
                val walletId: String
            ) {
                data class Beneficiary(
                    @SerializedName("email")
                    val email: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("phoneNumber")
                    val phoneNumber: String,
                    @SerializedName("ppp")
                    val ppp: Int,
                    @SerializedName("profileImage")
                    val profileImage: String,
                    @SerializedName("roleId")
                    val roleId: Int,
                    @SerializedName("userId")
                    val userId: Int
                )

                data class Remitter(
                    @SerializedName("email")
                    val email: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("phoneNumber")
                    val phoneNumber: String,
                    @SerializedName("ppp")
                    val ppp: Int,
                    @SerializedName("profileImage")
                    val profileImage: Any,
                    @SerializedName("roleId")
                    val roleId: Int,
                    @SerializedName("userId")
                    val userId: Int
                )

                data class TransactionInfo(
                    @SerializedName("amount")
                    val amount: Int,
                    @SerializedName("beneficiaryId")
                    val beneficiaryId: Int,
                    @SerializedName("createdAt")
                    val createdAt: String,
                    @SerializedName("description")
                    val description: String,
                    @SerializedName("expiresIn")
                    val expiresIn: Any,
                    @SerializedName("handShakingStatus")
                    val handShakingStatus: Boolean,
                    @SerializedName("iban")
                    val iban: Any,
                    @SerializedName("latitude")
                    val latitude: Double,
                    @SerializedName("longitude")
                    val longitude: Double,
                    @SerializedName("method")
                    val method: Int,
                    @SerializedName("paymentConfirmedAt")
                    val paymentConfirmedAt: String,
                    @SerializedName("processId")
                    val processId: String,
                    @SerializedName("processInfo")
                    val processInfo: String,
                    @SerializedName("remitterId")
                    val remitterId: Int,
                    @SerializedName("status")
                    val status: Int,
                    @SerializedName("transactionId")
                    val transactionId: String,
                    @SerializedName("type")
                    val type: Int,
                    @SerializedName("updatedAt")
                    val updatedAt: String
                )
            }
        }

        data class Wallet(
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
            val walletAddress: String
        )
    }
}