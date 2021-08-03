package com.app.emcashmerchant.data.modelrequest


import com.google.gson.annotations.SerializedName

data class SignupInitialRequestBody(
    @SerializedName("address")
    val address: String,
    @SerializedName("name")
    val businessName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("contactPersonName")
    val contactPersonName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("registeredNameOfBusiness")
    val registeredNameOfBusiness: String = "",
    @SerializedName("serviceDescription")
    val serviceDescription: String,
    @SerializedName("tradeLicenseIssuingAuthority")
    val tradeLicenseIssuingAuthority: String,
    @SerializedName("tradeLicenseNumber")
    val tradeLicenseNumber: String,
    @SerializedName("zipCode")
    val zipCode: String = "",
    @SerializedName("referenceId")
    val referenceId: String? = null
)