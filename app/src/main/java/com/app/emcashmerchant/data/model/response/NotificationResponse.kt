package com.app.emcashmerchant.data.model.response


import com.google.gson.annotations.SerializedName


data class NotificationResponse(
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
        val page: String,
        @SerializedName("rows")
        val rows: ArrayList<Row>,
        @SerializedName("totalPages")
        val totalPages: Int
    ) {
        data class Row(
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("isActive")
            val isActive: Boolean,
            @SerializedName("message")
            val message: String,
            @SerializedName("type")
            val type: Int,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("userId")
            val userId: String,
            @SerializedName("contactUserId")
            val contactUserId: String

        )
    }



}