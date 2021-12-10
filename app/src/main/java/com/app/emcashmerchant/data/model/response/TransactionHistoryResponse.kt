package com.app.emcashmerchant.data.model.response


import com.google.gson.annotations.SerializedName


data class TransactionHistoryResponse(
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
        val totalPages: Int
    ) {
        data class Row(
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
            @SerializedName("id")
            val id: String,
            @SerializedName("latitude")
            val latitude: Any,
            @SerializedName("longitude")
            val longitude: Any,
            @SerializedName("method")
            val method: Int,
            @SerializedName("paymentConfirmedAt")
            val paymentConfirmedAt: Any,
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
            @SerializedName("transferUserInfo")
            val transferUserInfo: TransferUserInfo,
            @SerializedName("type")
            val type: Int,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("walletTransactionInfo")
            val walletTransactionInfo: WalletTransactionInfo
        ) {
            data class TransferUserInfo(
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

            data class WalletTransactionInfo(
                @SerializedName("balance")
                val balance: Int,
                @SerializedName("mode")
                val mode: Int,
                @SerializedName("transactionId")
                val transactionId: Any,
                @SerializedName("userId")
                val userId: Int,
                @SerializedName("walletId")
                val walletId: Any,
                @SerializedName("walletTransactionId")
                val walletTransactionId: Any
            )
        }
    }

}

data class TransactionHistoryGroupViewModel(
    val date: String,
    val activities: List<TransactionHistoryResponse.Data.Row>
) {

}

data class TransactionHistoryDetails(
    val type: Int,
    val valueLoaded: String,
    val time: String,
    val changedValue: String,
    val Balance: String
) {

}