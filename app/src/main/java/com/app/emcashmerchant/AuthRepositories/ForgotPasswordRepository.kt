package com.app.emcashmerchant.AuthRepositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.ForgotPasswordRequest
import com.app.emcashmerchant.data.modelrequest.ResendOtpRequest
import com.app.emcashmerchant.data.modelrequest.ResetPasswordRequest
import com.app.emcashmerchant.data.modelrequest.VerifyOtpRequest
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class ForgotPasswordRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun performPasswordRequest(
        forgotPasswordRequest: ForgotPasswordRequest,
        onApiCallback: (status: Boolean, message: String?, result: ForgotPasswordResponse.Data?) -> Unit
    ) {
        api.performForgotPassword(forgotPasswordRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                it?.data?.let {
                    sessionStorage.setReferenceIdSession(it.referenceId.toString())
                    onApiCallback(true, null, it)
                }
            }
        )
    }

    fun performForgotPasswordOtpVerify(
        verifyOtpRequest: VerifyOtpRequest,
        onApiCallback: (status: Boolean, message: String?, result: VerifyOtpResponse.Data?) -> Unit
    ) {
        api.performForgotPasswordVerifyOtp(verifyOtpRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    onApiCallback(true, null, data)
                }
            }
        )
    }

    fun performForgotPasswordOtpResend(
        resendOtpRequest: ResendOtpRequest,
        onApiCallback: (status: Boolean, message: String?, result: ResendOtpResponse.Data?) -> Unit
    ) {
        api.performLoginResendOTP(resendOtpRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                it?.data?.let {
                    sessionStorage.setReferenceIdSession(it.referenceId.toString())
                    onApiCallback(true, null, it)
                }
            }
        )
    }

    fun performResetPassword(
        resetPasswordRequest: ResetPasswordRequest,
        onApiCallback: (status: Boolean, message: String?, result: ResetPasswordResponse?) -> Unit
    ) {
        api.performResetPassword(resetPasswordRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                it?.let {
                    onApiCallback(true, null, it)
                }
            }
        )
    }

}