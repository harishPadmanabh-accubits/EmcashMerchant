package com.app.emcashmerchant.data.model.response


import com.google.gson.annotations.SerializedName


data class WalletTransactionResponse(
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
        val rows: ArrayList<Row>,
        @SerializedName("totalPages")
        val totalPages: Int,
        @SerializedName("wallet")
        val wallet: Wallet
    ) {

        data class Row(
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
            @SerializedName("transactionInfo")
            val transactionInfo: TransactionInfo,
            @SerializedName("updatedAt")
            var updatedAt: String,
            @SerializedName("userId")
            val userId: String,
            @SerializedName("walletId")
            val walletId: String
        ) {

            data class TransactionInfo(
                @SerializedName("amount")
                val amount: Int,
                @SerializedName("beneficiary")
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
                val iban: String,
                @SerializedName("id")
                val id: String,
                @SerializedName("latitude")
                val latitude: String,
                @SerializedName("longitude")
                val longitude: String,
                @SerializedName("method")
                val method: Int,
                @SerializedName("paymentConfirmedAt")
                val paymentConfirmedAt: String,
                @SerializedName("referenceId")
                val referenceId: Any,
                @SerializedName("referenceInfo")
                val referenceInfo: Any,
                @SerializedName("remitter")
                val remitter: Remitter,
                @SerializedName("remitterId")
                val remitterId: String,
                @SerializedName("status")
                val status: Int,
                @SerializedName("type")
                val type: Int,
                @SerializedName("updatedAt")
                val updatedAt: String
            ) {

                data class Beneficiary(
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
            val walletAddress: String,
            @SerializedName("user")
            val user: User

        ) {
            data class User(
                @SerializedName("profileImage")
                val profileImage: String
            )
        }
    }

}


data class WalletActivityModel(
    val date: String,
    val activities: List<WalletTransactionResponse.Data.Row>
) {

}

data class TransactionItemUiModel(
    val date: String,
    val transactionList: List<WalletTransactionResponse.Data.Row>
)

data class WalletActivityDetails(
    val type: Int,
    val valueLoaded: String,
    val time: String,
    val changedValue: String,
    val Balance: String
) {

}
