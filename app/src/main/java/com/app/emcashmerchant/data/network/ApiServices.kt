package com.app.emcashmerchant.data.network

import com.app.emcashmerchant.data.modelrequest.*
import com.app.emcashmerchant.data.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("v1/merchants/signup/initial")
    fun performInitialSignup(
        @Body signupInitialRequestBody: SignupInitialRequestBody
    ): Call<SignupInitialResponse>

    @POST("v1/merchants/signup/otp/resend")
    fun performResendOTP(
        @Body resendOtpRequest: ResendOtpRequest
    ): Call<ResendOtpResponse>

    @POST("v1/merchants/signup/otp/verify")
    fun performVerifyOTP(
        @Body request: VerifyOtpRequest
    ): Call<VerifyOtpResponse>

    @GET("v1/merchants/signup/securityquestions")
    fun getSecurityQuestions(): Call<SecurityQuestionsResponse>

    @POST("v1/merchants/signup/security")
    fun performSecuritySignup(
        @Body signupSecurityRequestBody: SignupSecurityRequestBody
    ): Call<SignupSecurityResponse>

    @Multipart
    @POST("v1/merchants/signup/documents")
    fun performFinalSignupCommercialDoc(
        @Part("referenceId") description: RequestBody,
        @Part commercialRegistrationDoc: MultipartBody.Part
    ): Call<SignupFinalResponse>

    @Multipart
    @POST("v1/merchants/signup/documents")
    fun performFinalSignupBankDetailsDoc(
        @Part("referenceId") description: RequestBody,
        @Part bankDetailsDoc: MultipartBody.Part
    ): Call<SignupFinalResponse>

    @Multipart
    @POST("v1/merchants/signup/documents")
    fun performFinalSignupTradeLicenseDoc(
        @Part("referenceId") description: RequestBody,
        @Part tradeLicenseDoc: MultipartBody.Part
    ): Call<SignupFinalResponse>


    @POST("v1/merchants/signup/final")
    fun performFinalSignup(
        @Body finalSignupRequest: FinalSignupRequest
    ): Call<FinalRegistartionResponse>


    @POST("v1/merchants/auth/login")
    fun performLogin(
        @Body loginRequestBody: LoginResquestBody
    ): Call<LoginResponse>


    @POST("v1/merchants/auth/login/otp/verify")
    fun performLoginOtpVerification(
        @Body verifyOtpRequest: VerifyOtpRequest
    ): Call<LoginVerifyOtpResponse>

    @POST("v1/merchants/auth/pin/verify")
    fun performPinNumberVerification(
        @Header("Authorization") authentication: String,
        @Body pinNumberVerifyRequest: PinNumberVerifyRequest
    ): Call<PinNumberVerifyResponse>

    @POST("v1/merchants/auth/login/otp/resend")
    fun performLoginResendOTP(
        @Body resendOtpRequest: ResendOtpRequest
    ): Call<ResendOtpResponse>

    @POST("v1/merchants/auth/forgotpassword")
    fun performForgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): Call<ForgotPasswordResponse>


    @POST("v1/merchants/auth/forgotpassword/otp/verify")
    fun performForgotPasswordVerifyOtp(
        @Body verifyOtpRequest: VerifyOtpRequest
    ): Call<VerifyOtpResponse>


    @POST("v1/merchants/auth/forgotpassword/otp/resend")
    fun performForgotPasswordResendOtp(
        @Body resendOtpRequest: ResendOtpRequest
    ): Call<ResendOtpResponse>


    @POST("v1/merchants/auth/resetpassword")
    fun performResetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Call<ResetPasswordResponse>


    @POST("v1/merchants/auth/logout")
    fun performLogOut(
        @Header("Authorization") authentication: String
    ): Call<LogOutResponse>

    @Multipart
    @PUT("v1/merchants/profile")
    fun performUpdateProfile(
        @Header("Authorization") authentication: String,
        @Part profileImage: MultipartBody.Part
    ): Call<ProfileUpdateResponse>

    @GET("v1/merchants/profile")
    fun getProfileDetails(
        @Header("Authorization") authentication: String
    ): Call<ProfileDetailsResponse>

    @GET("v1/merchants/termsandconditions")
    fun getTermsConditions(
    ): Call<TermsConditionsResponse>

    @GET("v1/merchants/wallet")
    fun getWalletDetails(
        @Header("Authorization") authentication: String
    ): Call<WalletResponse>



    @POST("v1/merchants/wallet/topup")
    fun topUp(
        @Body topUpRequest: TopUpRequest,
        @Header("Authorization") authentication: String
    ): Call<TopUpResponse>



    @POST("v1/merchants/wallet/withdraw")
    fun withDraw(
        @Body withDrawRequest: WithDrawRequest,
        @Header("Authorization") authentication: String
    ): Call<WithDrawResponse>



}
