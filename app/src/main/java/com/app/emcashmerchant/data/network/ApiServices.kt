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

    @GET("v1/merchants/transactions/wallet")
    fun walletTransactionResponse(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<WalletTransactionResponse>


    @POST("v1/merchants/payments/qrcode/check")
    fun qrCodeCheck(
        @Body qrCodeRequest: CheckQrCodeRequest,
        @Header("Authorization") authentication: String
    ): Call<CheckQrCodeResponse>


    @POST("v1/merchants/payments/transfer")
    fun transferAmount(
        @Body transferAmountRequest: TransferAmountRequest,
        @Header("Authorization") authentication: String
    ): Call<TransferAmountResponse>

    @GET("v1/merchants/transactions/main/{reference_id}")
    fun paymentReceiptResponse(
        @Path("reference_id") reference_id: String,
        @Header("Authorization") authentication: String
    ): Call<PaymentReceiptResponse>

    @GET("v1/merchants/contacts")
    fun allContactsResponse(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String
    ): Call<AllContactResponse>


    @GET("v1/merchants/contacts/{userId}")
    fun getOneContactResponse(
        @Header("Authorization") authentication: String,
        @Path("userId") userId: String
    ): Call<CustomerContactResponse>

    @POST("v1/merchants/payments/initiate")
    fun intiatePayment(
        @Body intiateContactPaymentRequest: IntiateContactPaymentRequest,
        @Header("Authorization") authentication: String
    ): Call<IntiateContactPaymentResponse>



    @POST("v1/merchants/payments/qrcode/generate")
    fun generateQrCodeRequest(
        @Body generateQrCodeRequest: GenerateQrCodeRequest,
        @Header("Authorization") authentication: String
    ): Call<GenerateQrCodeResponse>


    @GET("v1/merchants/transactions/recent")
    fun recentTransaction(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<RecentTransactionResponse>



    @POST("v1/merchants/payments/request")
    fun requestPayment(
        @Header("Authorization") authentication: String,
        @Body  paymentRequest: PaymentRequest
    ): Call<PaymentRequestResponse>


    @GET("v1/merchants/transactions/main?")
    fun allTransactionHistoryReponse(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("mode") mode: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<TransactionHistoryResponse>

    @GET("v1/merchants/contacts/{user_id}/transactions")
    fun getChatResponse(
        @Header("Authorization") authentication: String,
        @Path("user_id") user_id: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int

    ): Call<PaymentChatResponse>



}
