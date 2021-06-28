package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName

data class SignupInitialRequestBody(
    @SerializedName("address")
    val address: String,
    @SerializedName("businessName")
    val businessName: String,
    @SerializedName("contactPersonName")
    val contactPersonName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("registeredNameOfBusiness")
    val registeredNameOfBusiness: String,
    @SerializedName("serviceDescription")
    val serviceDescription: String,
    @SerializedName("tradeLicenseIssuingAuthority")
    val tradeLicenseIssuingAuthority: String,
    @SerializedName("tradeLicenseNumber")
    val tradeLicenseNumber: String,
    @SerializedName("zipCode")
    val zipCode: String
)