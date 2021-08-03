package com.app.emcashmerchant.Authviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.*
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.ForgotPasswordRepository
import com.app.emcashmerchant.utils.extensions.default
import timber.log.Timber

class ForgotPasswordViewModel(val app: Application): AndroidViewModel(app) {
    var performPasswordRequestStatus= MutableLiveData<ApiMapper<ForgotPasswordResponse.Data>>()
    var performForgotPasswordOtpStatus = MutableLiveData<ApiMapper<VerifyOtpResponse.Data>>()
    var performForgotPasswordOtpResendStatus = MutableLiveData<ApiMapper<ResendOtpResponse.Data>>()
    var performResetPasswordStatus = MutableLiveData<ApiMapper<ResetPasswordResponse>>()

    val repository = ForgotPasswordRepository(app)
    var passwordVisibiltyData = MutableLiveData<PasswordVisibilty>()
        .default(PasswordVisibilty.PSWD_HIDE)


    fun performPasswordRequest(
        email: String,
        questionOneId:String,
        questionTwoId: String,
        questionOneAnswer:String,
        questionTwoAnswer: String

    ){
        performPasswordRequestStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val forgotPasswordRequest =
            ForgotPasswordRequest(
                email,questionOneAnswer,questionOneId,questionTwoAnswer,questionTwoId
            )
        repository.performPasswordRequest(forgotPasswordRequest) { status, message, result ->
            when (status) {
                true -> {
                    performPasswordRequestStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performPasswordRequestStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun performForgotPasswordOtpVerify(
        otp: String,refId:String
    ) {
        performForgotPasswordOtpStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val verifyOtprequest =
            VerifyOtpRequestReset(
                otp,refId
            )
        repository.performForgotPasswordOtpVerify(verifyOtprequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    performForgotPasswordOtpStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performForgotPasswordOtpStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun performForgotPasswordOtpResend(
        refId: String
    ){
        performForgotPasswordOtpResendStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val resendOtpRequest =
            ResendOtpRequest(refId)
        repository.performForgotPasswordOtpResend(resendOtpRequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    performForgotPasswordOtpResendStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performForgotPasswordOtpResendStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }
        }
    }

    fun performResetPassword(
        password:String,refId: String
    ){
        performResetPasswordStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val resetPasswordRequest =
            ResetPasswordRequest(
                password,refId
            )
        repository.performResetPassword(resetPasswordRequest) { status, message, result ->
            when (status) {
                true -> {
                    performResetPasswordStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performResetPasswordStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

}