package com.app.emcashmerchant.data.network

import com.app.emcashmerchant.data.model.request.*
import com.app.emcashmerchant.data.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface EmCashApiServices {

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
        @Body request: VerifyOtpRequestRegister
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


    @Multipart
    @POST("v1/admin/merchants/additional-documents")
    fun reUploadCommercialDoc(
        @Part("finalSubmit") finalSubmit: RequestBody,
        @Part commercialRegistrationDoc: MultipartBody.Part
    ): Call<ReUploadResponse>

    @Multipart
    @POST("v1/admin/merchants/additional-documents")
    fun reUploadBankDetailsDoc(
        @Part("finalSubmit") finalSubmit: RequestBody,
        @Part bankDetailsDoc: MultipartBody.Part
    ): Call<ReUploadResponse>

    @Multipart
    @POST("v1/admin/merchants/additional-documents")
    fun reUploadSignupTradeLicenseDoc(
        @Part("finalSubmit") finalSubmit: RequestBody,
        @Part tradeLicenseDoc: MultipartBody.Part
    ): Call<ReUploadResponse>


    @Multipart
    @POST("v1/admin/merchants/additional-documents")
    fun submitForReview(
        @Part("finalSubmit") finalSubmit: RequestBody,
        @Part tradeLicenseDoc: MultipartBody.Part?,
        @Part bankDetailsDoc: MultipartBody.Part?,
        @Part commercialRegistrationDoc: MultipartBody.Part?
    ): Call<ReUploadResponse>


    @GET("v1/admin/merchants/upload-document/user_details")
    fun reUploadUserDetails(
    ): Call<ReUploadUserDeatilsResponse>


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
        @Body pinNumberVerifyRequest: PinNumberVerifyRequest
    ): Call<PinNumberVerifyResponse>

    @POST("v1/merchants/auth/login/otp/resend")
    fun performLoginResendOTP(
        @Body resendOtpRequest: ResendOtpRequest
    ): Call<ResendOtpResponse>

    @POST("v1/merchants/auth/forgotpassword/otp/resend")
    fun performForgotResendOTP(
        @Body resendOtpRequest: ResendOtpRequest
    ): Call<ResendOtpResponse>


    @POST("v1/merchants/auth/forgotpassword")
    fun performForgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): Call<ForgotPasswordResponse>


    @POST("v1/merchants/auth/forgotpassword/otp/verify")
    fun performForgotPasswordVerifyOtp(
        @Body verifyOtpRequest: VerifyOtpRequestReset
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
    ): Call<LogOutResponse>

    @Multipart
    @PUT("v1/merchants/profile")
    fun performUpdateProfile(
        @Part profileImage: MultipartBody.Part
    ): Call<ProfileUpdateResponse>

    @GET("v1/merchants/profile")
    fun getProfileDetails(
    ): Call<ProfileDetailsResponse>

    @GET("v1/merchants/termsandconditions")
    fun getTermsConditions(
    ): Call<TermsConditionsResponse>

    @GET("v1/merchants/wallet")
    fun getWalletDetails(
    ): Call<WalletResponse>


    @POST("v1/merchants/wallet/topup")
    fun topupEmCash(
        @Body topUpRequest: TopUpRequest
    ): Call<TopUpResponse>


    @POST("v1/merchants/wallet/withdraw")
    fun withDrawEmCash(
        @Body withDrawRequest: WithDrawRequest
    ): Call<WithDrawResponse>

    @GET("v1/merchants/transactions/wallet")
    fun getWalletTransactions(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<WalletTransactionResponse>


    @GET("v1/merchants/transactions/wallet/group")
    suspend fun getWalletTransactionsPaged(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<GroupedWalletTransactionResponse>


    @POST("v1/merchants/payments/qrcode/check")
    fun checkQR(
        @Body qrCodeRequest: CheckQrCodeRequest
    ): Call<CheckQrCodeResponse>


    @POST("v1/merchants/payments/transfer")
    fun transferAmount(
        @Body transferAmountRequest: TransferAmountRequest,
        @Header("Authorization") authentication: String
    ): Call<TransferAmountResponse>


    @POST("v1/merchants/payments/reject")
    fun rejectPayment(
        @Body request: RejectAcceptRequest
    ): Call<RejectResponse>


    @POST("v1/merchants/payments/approve")
    fun acceptPayment(
        @Body request: RejectAcceptRequest
    ): Call<AcceptResponse>


    @GET("v1/merchants/transactions/main/{reference_id}")
    fun getPaymentReceipt(
        @Path("reference_id") reference_id: String
    ): Call<PaymentReceiptResponse>

    @GET("v1/merchants/contacts")
    fun getAllContacts(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String
    ): Call<AllContactResponse>


    @GET("v1/merchants/contacts/group")
    suspend fun getAllContactsPaged(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String
    ): Response<GroupedContactsResponse>


    @GET("v1/merchants/contacts/{userId}")
    fun getOneContactResponse(
        @Path("userId") userId: String
    ): Call<CustomerContactResponse>

    @POST("v1/merchants/payments/initiate")
    fun initiatePayment(
        @Body initiateContactPaymentRequest: InitiateContactPaymentRequest
    ): Call<IntiateContactPaymentResponse>


    @POST("v1/merchants/payments/qrcode/generate")
    fun generateQrCodeRequest(
        @Body generateQrCodeRequest: GenerateQrCodeRequest
    ): Call<GenerateQrCodeResponse>


    @GET("v1/merchants/transactions/recent")
    fun getRecentTransactions(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<RecentTransactionResponse>


    @GET("v1/merchants/transactions/recent")
    suspend fun getAllTransactedUsers(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<RecentTransactionResponse>


    @POST("v1/merchants/payments/request")
    fun requestPayment(
        @Body paymentRequest: PaymentRequest
    ): Call<PaymentRequestResponse>


    @GET("v1/merchants/transactions/main/group?")
    suspend fun getTransactionHistory(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("mode") mode: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("status") status: String,
        @Query("type") type: String
    ): Response<GroupedTransactionHistoryResponse>

    @GET("v1/merchants/contacts/{user_id}/transactions/group")
    fun getGroupedChatResponse(
        @Path("user_id") user_id: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<GroupedChatHistoryResponse>


    @GET("v1/merchants/contacts/{user_id}/transactions/group")
    suspend fun getPagingGroupedChatResponse(
        @Path("user_id") user_id: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<GroupedChatHistoryResponse>


    @POST("v1/merchants/contacts/{user_id}/block")
    fun blockContact(
        @Path("user_id") user_id: Int
    ): Call<BlockedResponse>

    @POST("v1/merchants/contacts/{user_id}/unblock")
    fun unBlockContact(
        @Path("user_id") user_id: Int
    ): Call<UnblockedResponse>

    @GET("v1/merchants/notification")
    fun getNotifications(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<NotificationResponse>

    @GET("v1/merchants/notification/group-by-date")
    suspend fun getGroupedNotifications(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<GroupedNotificationResponse>

    @POST("v1/merchants/auth/refresh")
    fun refreshToken(
        @Header("Authorization") auth:String,
        @Body refreshTokenRequest: RefreshTokenRequest
    ): RefreshTokenResponse


    @GET("v1/merchants/empay/cards")
    fun getBankCard(
    ): Call<BankCardsListingResponse>


    @POST("v1/merchants/empay/payment-through-new-card")
    fun paymentByNewCard(
        @Body paymentByNewCardRequest: PaymentByNewCardRequest
    ): Call<PaymentByNewCardResponse>

    @POST("v1/merchants/empay/payment-through-existing-card")
    fun paymentByExistingCard(
        @Body paymentByExistingCardRequest: PaymentByExistingCardRequest
    ): Call<PaymentByExisitingCardResponse>


    @POST("v1/merchants/bank/details")
    fun addBankDetails(
        @Body addBankDetailsRequest: AddBankDetailsRequest
    ): Call<AddBankDetailsResponse>


    @PUT("v1/merchants/bank/details")
    fun editBankDetails(
        @Body editBankDetailsRequest: EditBankDetailsRequest
    ): Call<EditBankDetailsResponse>


    @GET("v1/merchants/bank/details")
    fun getBankDetails(
    ): Call<BankDetailsResponse>


    @POST("v1/merchants/empay/payer-authentication")
    fun authenticatePayer(
        @Body payerAuthenticatorRequest: PayerAuthenticatorRequest
    ): Call<PayerAuthenticatorResponse>


    @POST("v1/merchants/transactions/receipt")
    fun generateReceipt(
        @Body recieptRequest: RecieptRequest
    ): Call<RecieptResponse>

    @GET("v1/merchants/notification/{id}")
    fun onNotificationItemClick(
        @Path("id") id: String
    ): Call<NotificationClickResponse>


}
