package com.app.emcashmerchant.Authviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.*
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.LoginAuthRepository
import com.app.emcashmerchant.utils.extensions.default
import timber.log.Timber

class LoginViewModel(val app: Application) : AndroidViewModel(app) {

    var initialLoginStatus = MutableLiveData<ApiMapper<LoginResponse.Data>>()
    var loginOtpStatus = MutableLiveData<ApiMapper<LoginVerifyOtpResponse.Data>>()
    var pinNumberStatus = MutableLiveData<ApiMapper<PinNumberVerifyResponse>>()
    var resendOtpStatus = MutableLiveData<ApiMapper<ResendOtpResponse.Data>>()


    val repository = LoginAuthRepository(app)
    var passwordVisibiltyData = MutableLiveData<PasswordVisibilty>()
        .default(PasswordVisibilty.PSWD_HIDE)

    fun performInitialLogin(
        email: String,
        password: String
    ) {
        initialLoginStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val loginRequestBody =
            LoginResquestBody(
                email,
                password
            )
        repository.performLogin(loginRequestBody) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    initialLoginStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    initialLoginStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun performVerifyOtp(
        otp: String, refId: String
    ) {
        loginOtpStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val verifyOtprequest =
            VerifyOtpRequest(
                otp, refId
            )
        repository.performVerifyOtp(verifyOtprequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    loginOtpStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    loginOtpStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun performPinNumberVerify(
        pin: Int
    ) {
        pinNumberStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val pinNumberVerifyRequest =
            PinNumberVerifyRequest(
                pin
            )
        repository.performVerifyPinNumber(pinNumberVerifyRequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    pinNumberStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    pinNumberStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun performResendOtp(refId: String) {
        resendOtpStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val resendOtpRequest =
            ResendOtpRequest(refId)
        repository.performResendOtp(resendOtpRequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    resendOtpStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    resendOtpStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }
        }
    }


}


enum class PasswordVisibilty {
    PSWD_SHOW,
    PSWD_HIDE
}