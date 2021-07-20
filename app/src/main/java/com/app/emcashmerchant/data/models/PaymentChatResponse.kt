package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class PaymentChatResponse(
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
        @SerializedName("contact")
        val contact: Contact,
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
        data class Contact(
            @SerializedName("email")
            val email: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("isBlocked")
            val isBlocked: Boolean,
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
            val latitude: Double,
            @SerializedName("longitude")
            val longitude: Double,
            @SerializedName("method")
            val method: Int,
            @SerializedName("paymentConfirmedAt")
            val paymentConfirmedAt: String,
            @SerializedName("processId")
            val processId: Any,
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
            @SerializedName("isReciever")
            val isReciever: Boolean,
            @SerializedName("mode")
            val mode: Int

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
        }
    }


    data class ChatTransactionViewModel(
        val date: String,
        val activities: List<PaymentChatResponse.Data.Row>
    ) {

    }
}
