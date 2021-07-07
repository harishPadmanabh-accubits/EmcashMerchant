package com.app.emcashmerchant.Authviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.AuthRepository
import com.app.emcashmerchant.data.modelrequest.*
import timber.log.Timber
import java.io.File

class RegisterViewModel(val app: Application) : AndroidViewModel(app) {
    val repository = AuthRepository(app)

    var initialSignupStatus = MutableLiveData<ApiMapper<SignupInitialResponse.Data>>()
    var securitySignupStatus = MutableLiveData<ApiMapper<SignupSecurityResponse>>()
    var resendOtpStatus = MutableLiveData<ApiMapper<ResendOtpResponse.Data>>()
    var verifyOtpStatus = MutableLiveData<ApiMapper<VerifyOtpResponse.Data>>()
    var securityQuestionStatus = MutableLiveData<ApiMapper<SecurityQuestionsResponse>>()

    var commercialDocumentStatus = MutableLiveData<ApiMapper<SignupFinalResponse>>()
    var tradeDocumentStatus = MutableLiveData<ApiMapper<SignupFinalResponse>>()
    var bankDetailsDocumentStatus = MutableLiveData<ApiMapper<SignupFinalResponse>>()
    var finalSignupResponseStatus = MutableLiveData<ApiMapper<FinalRegistartionResponse>>()

    fun performInitialSignup(
        address: String,
        businessName: String,
        email: String,
        contactPersonName: String,
        phoneNumber: String,
        registeredNameOfBusiness: String,
        serviceDescription: String,
        tradeLicenseIssuingAuthority: String,
        tradeLicenseNumber: String,
        zipCode: String
    ) {
        initialSignupStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val signupRequestBody =
            SignupInitialRequestBody(
                address,
                businessName,
                email,
                contactPersonName,
                phoneNumber,
                registeredNameOfBusiness,
                serviceDescription,
                tradeLicenseIssuingAuthority,
                tradeLicenseNumber,
                zipCode
            )
        repository.performInitialSignup(signupRequestBody) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    initialSignupStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    initialSignupStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

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


    fun performVerifyOtp(otp: String, refId: String) {
        verifyOtpStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        val verifyOtpRequest = VerifyOtpRequest(otp, refId)
        repository.verifyOtp(verifyOtpRequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    verifyOtpStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    verifyOtpStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }
        }
    }

    fun getSecurityQuestions() {
        securityQuestionStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        repository.getSecurityQuestions() { status, message, result ->
            when (status) {
                true -> {
                    securityQuestionStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    securityQuestionStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }
        }
    }

    fun performSecuritySignup(
        finalSignUpData: SignupSecurityRequestBody
    ) {
        securitySignupStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.performSecuritySignup(finalSignUpData) { status, message, result ->
            when (status) {
                true -> {
                    securitySignupStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)


                }
                false -> {
                    securitySignupStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }

        }
    }

    fun uploadCommercialRegistrationDoc(
        file: File
    ) {
        commercialDocumentStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        repository.uploadCommercialRegistrationDoc(file) { status, message, result ->
            when (status) {
                true -> {
                    commercialDocumentStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)

                }
                false -> {
                    commercialDocumentStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }

        }
    }

    fun uploadBankDetailsDoc(
        file: File
    ) {
        bankDetailsDocumentStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        repository.uploadPerformBankDetailsDoc(file) { status, message, result ->
            when (status) {
                true -> {
                    bankDetailsDocumentStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)

                }
                false -> {
                    bankDetailsDocumentStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }

        }
    }

    fun uploadTradeLicenseDoc(
        file: File
    ) {
        tradeDocumentStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        repository.uploadPerformSignupTradeLicenseDoc(file) { status, message, result ->
            when (status) {
                true -> {
                    tradeDocumentStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)

                }
                false -> {
                    tradeDocumentStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }

        }
    }

    fun finalSignup() {
        finalSignupResponseStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        repository.performFinalSignup() { status, message, result ->
            when (status) {
                true -> {
                    finalSignupResponseStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)

                }
                false -> {
                    finalSignupResponseStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)
                }
            }

        }

    }
}