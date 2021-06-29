package com.app.emcashmerchant.data.repositories

import android.content.Context
import com.app.emcashmerchant.data.AppPreferences
import com.app.emcashmerchant.data.models.ResendOtpRequest
import com.app.emcashmerchant.data.models.ResendOtpResponse
import com.app.emcashmerchant.data.models.SignupInitialRequestBody
import com.app.emcashmerchant.data.models.SignupInitialResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class AuthRepository(val context: Context) {

    private val appPreferences = AppPreferences.init(context)
    private val api = ApiManger(context).api

    fun performInitialSignup(
        signUpData: SignupInitialRequestBody,
        onApiCallback: (status: Boolean, message: String?, result: SignupInitialResponse.Data?) -> Unit
    ) {
        api.performInitialSignup(signUpData).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    appPreferences.refId = it.referenceId
                    onApiCallback(true, null, data)
                }
            })
    }


    fun performResendOtp(
        resendOtpRequest: ResendOtpRequest,
        onApiCallback: (status: Boolean, message: String?, result: ResendOtpResponse.Data?) -> Unit
    ) {
        api.performResendOTP(resendOtpRequest).awaitResponse(
            onFailure = {
                onApiCallback(false,it,null)
            },onSuccess = {
                it?.data?.let {
                    onApiCallback(true,null,it)
                }
            }
        )
    }
}





