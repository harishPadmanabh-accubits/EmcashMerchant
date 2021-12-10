package com.app.emcashmerchant.data.model.request

import com.google.gson.annotations.SerializedName

class SignupSecurityRequestBody (
    @SerializedName("referenceId")
    val referenceId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("questionOneId")
    val questionOneId: String ="",
    @SerializedName("questionOneAnswer")
    val questionOneAnswer: String = "",
    @SerializedName("questionTwoId")
    val questionTwoId: String,
    @SerializedName("questionTwoAnswer")
    val questionTwoAnswer: String = ""
    )