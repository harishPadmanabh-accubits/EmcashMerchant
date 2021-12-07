package com.app.emcashmerchant.ui.login.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.LoginResquestBody
import com.app.emcashmerchant.data.modelrequest.PinNumberVerifyRequest
import com.app.emcashmerchant.data.modelrequest.ResendOtpRequest
import com.app.emcashmerchant.data.modelrequest.VerifyOtpRequest
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.LoginAuthRepository

class LoginViewModel(val app: Application) : AndroidViewModel(app) {

    val initialLoginStatus = MutableLiveData<ApiMapper<LoginResponse.Data>>()
    val loginOtpStatus = MutableLiveData<ApiMapper<LoginVerifyOtpResponse.Data>>()
    val pinNumberStatus = MutableLiveData<ApiMapper<PinNumberVerifyResponse>>()
    val resendOtpStatus = MutableLiveData<ApiMapper<ResendOtpResponse.Data>>()
    val initialLogOutResponseStatus = MutableLiveData<ApiMapper<LogOutResponse>>()


    private  val repository = LoginAuthRepository(app)
     var fcmToken: String? = null



    fun performLogout(
    ) {
        initialLogOutResponseStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.performLogout { status, message, result ->
            when (status) {
                true -> {
                    initialLogOutResponseStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    initialLogOutResponseStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

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
        otp: String, refId: String,fcmToken:String
    ) {
        loginOtpStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val verifyOtprequest =
            VerifyOtpRequest(
                otp, refId,fcmToken
            )
        repository.performVerifyOtp(verifyOtprequest) { status, message, result ->
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


