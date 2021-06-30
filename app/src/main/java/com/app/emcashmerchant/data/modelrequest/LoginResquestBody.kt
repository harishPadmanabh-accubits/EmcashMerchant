package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName

data class LoginResquestBody(
    @SerializedName("email")
    val email: String="",
    @SerializedName("password")
    val password: String=""
)