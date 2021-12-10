package com.app.emcashmerchant.data.model.response


import com.google.gson.annotations.SerializedName


data class GroupedContactsResponse(
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
        val totalPages: Int
    ) {
        data class Row(
            @SerializedName("contacts")
            val contacts: List<Contact>,
            @SerializedName("key")
            val key: String
        ) {
            data class Contact(
                @SerializedName("address")
                val address: Any,
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
                @SerializedName("name")
                val name: String,
                @SerializedName("phoneNumber")
                val phoneNumber: String,
                @SerializedName("ppp")
                val ppp: Int,
                @SerializedName("profileImage")
                val profileImage: Any,
                @SerializedName("qrCode")
                val qrCode: Any,
                @SerializedName("roleId")
                val roleId: Int,
                @SerializedName("status")
                val status: Any,
                @SerializedName("updatedAt")
                val updatedAt: String,
                @SerializedName("zipCode")
                val zipCode: Any
            )
        }
    }
}