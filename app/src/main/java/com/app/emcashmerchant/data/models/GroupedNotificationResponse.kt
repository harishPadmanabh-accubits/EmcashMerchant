package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class GroupedNotificationResponse(
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
        val limit: String,
        @SerializedName("page")
        val page: Int,
        @SerializedName("rows")
        val rows: List<Row>,
        @SerializedName("totalPages")
        val totalPages: Int
    ) {
        data class Row(
            @SerializedName("key")
            val key: String,
            @SerializedName("notifications")
            val notifications: List<Notification>
        ) {
            data class Notification(
                @SerializedName("contactUserId")
                val contactUserId: Int,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("isActive")
                val isActive: Boolean,
                @SerializedName("message")
                val message: String,
                @SerializedName("type")
                val type: Int,
                @SerializedName("userId")
                val userId: Int
            )
        }
    }
}