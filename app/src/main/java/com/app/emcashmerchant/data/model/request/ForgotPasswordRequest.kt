package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class ForgotPasswordRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("questionOneAnswer")
    val questionOneAnswer: String,
    @SerializedName("questionOneId")
    val questionOneId: String,
    @SerializedName("questionTwoAnswer")
    val questionTwoAnswer: String,
    @SerializedName("questionTwoId")
    val questionTwoId: String
)