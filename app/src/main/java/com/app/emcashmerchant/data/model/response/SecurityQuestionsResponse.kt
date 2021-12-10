package com.app.emcashmerchant.data.model.response

import com.google.gson.annotations.SerializedName

data class SecurityQuestionsResponse(

    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("question")
        val question: String,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("updatedAt")
        val updatedAt: String
    )
}