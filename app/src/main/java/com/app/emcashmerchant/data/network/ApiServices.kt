package com.app.emcashmerchant.data.network

import com.app.emcashmerchant.data.models.SignupInitialRequestBody
import com.app.emcashmerchant.data.models.SignupInitialResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("v1/merchants/signup/initial")
    fun performInitialSignup(@Body signupInitialRequestBody: SignupInitialRequestBody):Call<SignupInitialResponse>

}