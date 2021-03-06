package com.app.emcashmerchant.ui.forgotPassword.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.model.request.*
import com.app.emcashmerchant.data.model.response.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.Repositories.ForgotPasswordRepository
import timber.log.Timber

class ForgotPasswordViewModel(val app: Application) : AndroidViewModel(app) {
    val performPasswordRequestStatus = MutableLiveData<ApiMapper<ForgotPasswordResponse.Data>>()
    val performForgotPasswordOtpStatus = MutableLiveData<ApiMapper<VerifyOtpResponse.Data>>()
    val performForgotPasswordOtpResendStatus = MutableLiveData<ApiMapper<ResendOtpResponse.Data>>()
    val performResetPasswordStatus = MutableLiveData<ApiMapper<ResetPasswordResponse>>()

    private val repository = ForgotPasswordRepository(app)


    fun performPasswordRequest(
        email: String,
        questionOneId: String,
        questionTwoId: String,
        questionOneAnswer: String,
        questionTwoAnswer: String

    ) {
        performPasswordRequestStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val forgotPasswordRequest =
            ForgotPasswordRequest(
                email, questionOneAnswer, questionOneId, questionTwoAnswer, questionTwoId
            )
        repository.performPasswordRequest(forgotPasswordRequest) { status, message, result ->
            when (status) {
                true -> {
                    performPasswordRequestStatus.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performPasswordRequestStatus.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun performForgotPasswordOtpVerify(
        otp: String, refId: String
    ) {
        performForgotPasswordOtpStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val verifyOtprequest =
            VerifyOtpRequestReset(
                otp, refId
            )
        repository.performForgotPasswordOtpVerify(verifyOtprequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    performForgotPasswordOtpStatus.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performForgotPasswordOtpStatus.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun performForgotPasswordOtpResend(
        refId: String
    ) {
        performForgotPasswordOtpResendStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val resendOtpRequest =
            ResendOtpRequest(refId)
        repository.performForgotPasswordOtpResend(resendOtpRequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    performForgotPasswordOtpResendStatus.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performForgotPasswordOtpResendStatus.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }
        }
    }

    fun performResetPassword(
        password: String, refId: String
    ) {
        performResetPasswordStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val resetPasswordRequest =
            ResetPasswordRequest(
                password, refId
            )
        repository.performResetPassword(resetPasswordRequest) { status, message, result ->
            when (status) {
                true -> {
                    performResetPasswordStatus.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    performResetPasswordStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

}