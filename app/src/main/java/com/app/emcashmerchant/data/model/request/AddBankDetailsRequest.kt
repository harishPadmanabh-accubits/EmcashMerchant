package com.app.emcashmerchant.data.model.request


import com.google.gson.annotations.SerializedName


data class AddBankDetailsRequest(
    @SerializedName("beneficiaryName")
    val beneficiaryName: String,
    @SerializedName("branchName")
    val branchName: String,
    @SerializedName("iBanNumber")
    val iBanNumber: String,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("swiftCode")
    val swiftCode: String,
    @SerializedName("branchCode")
    val branchCode: String
)