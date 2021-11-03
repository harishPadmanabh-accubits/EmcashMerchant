package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.*
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class LoginAuthRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api


    fun performLogout(

        onApiCallback: (status: Boolean, message: String?, result: LogOutResponse?) -> Unit
    ) {
        api.performLogOut("Bearer ${sessionStorage.accesToken}").awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {data ->
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }

    fun performLogin(
        loginRequestBody: LoginResquestBody,
        onApiCallback: (status: Boolean, message: String?, result: LoginResponse.Data?) -> Unit
    ) {

        api.performLogin(loginRequestBody).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = { response ->
                response?.data?.let {
                    onApiCallback(true, null, it)
                }
            }
        )
    }

    fun performVerifyOtp(
        verifyOtpRequest: VerifyOtpRequest,
        onApiCallback: (status: Boolean, message: String?, result: LoginVerifyOtpResponse.Data?) -> Unit
    ) {

        api.performLoginOtpVerification(verifyOtpRequest).awaitResponse(
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

    fun performVerifyPinNumber(
        pinNumberVerifyRequest: PinNumberVerifyRequest,
        onApiCallback: (status: Boolean, message: String?, result: PinNumberVerifyResponse?) -> Unit
    ) {

        api.performPinNumberVerification("Bearer ${sessionStorage.accesToken}",pinNumberVerifyRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {data ->
                data?.let {
                    onApiCallback(true, null, data)
                }
            }
        )
    }

    fun performResendOtp(
        resendOtpRequest: ResendOtpRequest,
        onApiCallback: (status: Boolean, message: String?, result: ResendOtpResponse.Data?) -> Unit
    ) {
        api.performLoginResendOTP(resendOtpRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = { response ->
                response?.data?.let {
                    sessionStorage.referenceId=it.referenceId
                    onApiCallback(true, null, it)
                }
            }
        )
    }




}