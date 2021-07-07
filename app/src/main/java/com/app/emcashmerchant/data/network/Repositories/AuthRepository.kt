package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import android.webkit.MimeTypeMap
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.*
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse
import com.app.emcashmerchant.utils.extensions.getMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class AuthRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
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
                    sessionStorage.referenceId=it.referenceId
                    onApiCallback(true, null, data)
                }
            })
    }


    fun verifyOtp(
        verifyOtpRequest: VerifyOtpRequest,
        onApiCallback: (status: Boolean, message: String?, result: VerifyOtpResponse.Data?) -> Unit
    ) {
        api.performVerifyOTP(verifyOtpRequest).awaitResponse(
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

    fun performResendOtp(
        resendOtpRequest: ResendOtpRequest,
        onApiCallback: (status: Boolean, message: String?, result: ResendOtpResponse.Data?) -> Unit
    ) {
        api.performResendOTP(resendOtpRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                it?.data?.let {
                    sessionStorage.referenceId=it.referenceId
                    onApiCallback(true, null, it)
                }
            }
        )
    }

    fun getSecurityQuestions(onApiCallback: (status: Boolean, message: String?, result: SecurityQuestionsResponse?) -> Unit) {
        api.getSecurityQuestions().awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                it?.let {
                    onApiCallback(true, null, it)
                }
            }
        )
    }

    fun performSecuritySignup(
        finalSignUpData: SignupSecurityRequestBody,
        onApiCallback: (status: Boolean, message: String?, result: SignupSecurityResponse?) -> Unit
    ) {
        api.performSecuritySignup(
            finalSignUpData
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                it?.let {
                    onApiCallback(true, null, it)
                }
            }
        )

    }

    fun uploadCommercialRegistrationDoc(
        file: File,
        onApiCallback: (status: Boolean, message: String?, result: SignupFinalResponse?) -> Unit
    ) {
        val newFile:String=file.toString().replace("\\s".toRegex(), "")
        val extension = MimeTypeMap.getFileExtensionFromUrl(newFile.toString())
        val referenceID = sessionStorage.referenceIdSecurity.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val document = file.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("commercialRegistrationDoc", file.name, document)

        api.performFinalSignupCommercialDoc(
            referenceID,
            filePart
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }

    fun uploadPerformBankDetailsDoc(
        file: File,
        onApiCallback: (status: Boolean, message: String?, result: SignupFinalResponse?) -> Unit
    ) {
        val newFile:String=file.toString().replace("\\s".toRegex(), "")
        val extension = MimeTypeMap.getFileExtensionFromUrl(newFile.toString())
        val referenceID =  sessionStorage.referenceIdSecurity.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val document = file.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("bankDetailsDoc", file.name, document)
        api.performFinalSignupBankDetailsDoc(
            referenceID,
            filePart
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }

    fun uploadPerformSignupTradeLicenseDoc(
        file: File,
        onApiCallback: (status: Boolean, message: String?, result: SignupFinalResponse?) -> Unit
    ) {
        val newFile:String=file.toString().replace("\\s".toRegex(), "")
        val extension = MimeTypeMap.getFileExtensionFromUrl(newFile.toString())
        val referenceID =  sessionStorage.referenceIdSecurity.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val document = file.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("tradeLicenseDoc", file.name, document)


        api.performFinalSignupTradeLicenseDoc(
            referenceID,
            filePart
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }



    fun performFinalSignup(
        onApiCallback: (status: Boolean, message: String?, result: FinalRegistartionResponse?) -> Unit
    ) {

        val finalSignupRequest=FinalSignupRequest(sessionStorage.referenceIdSecurity.toString())
        api.performFinalSignup(
            finalSignupRequest
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }



}





