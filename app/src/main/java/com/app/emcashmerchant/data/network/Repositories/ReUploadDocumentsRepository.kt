package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import android.webkit.MimeTypeMap
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.ReUploadResponse
import com.app.emcashmerchant.data.models.ReUploadUserDeatilsResponse
import com.app.emcashmerchant.data.models.SignupFinalResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse
import com.app.emcashmerchant.utils.extensions.getMediaType
import com.app.emcashmerchant.utils.extensions.isNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ReUploadDocumentsRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api
    lateinit var filePart_trade: MultipartBody.Part
    lateinit var filePart_bank: MultipartBody.Part
    lateinit var filePart_commercial: MultipartBody.Part

    fun reUploadTradeLicenseDoc(
        token: String,
        file: File, finalSubmit: String,
        onApiCallback: (status: Boolean, message: String?, result: ReUploadResponse?) -> Unit
    ) {
        val newFile: String = file.toString().replace("\\s".toRegex(), "")
        val extension = MimeTypeMap.getFileExtensionFromUrl(newFile.toString())
        val document = file.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("tradeLicenseDoc", file.name, document)
        val finalSubmit = finalSubmit.toRequestBody("text/plain".toMediaTypeOrNull())


        api.reUploadSignupTradeLicenseDoc(
            "Bearer ${token}",
            finalSubmit,
            filePart
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }

    fun reUploadBankDetailsDoc(
        token: String,
        file: File, finalSubmit: String,
        onApiCallback: (status: Boolean, message: String?, result: ReUploadResponse?) -> Unit
    ) {
        val newFile: String = file.toString().replace("\\s".toRegex(), "")
        val extension = MimeTypeMap.getFileExtensionFromUrl(newFile.toString())
        val document = file.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("tradeLicenseDoc", file.name, document)
        val finalSubmit = finalSubmit.toRequestBody("text/plain".toMediaTypeOrNull())


        api.reUploadBankDetailsDoc(
            "Bearer ${token}",
            finalSubmit,
            filePart
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }

    fun reUploadCommercialDoc(
        token: String,
        file: File, finalSubmit: String,
        onApiCallback: (status: Boolean, message: String?, result: ReUploadResponse?) -> Unit
    ) {
        val newFile: String = file.toString().replace("\\s".toRegex(), "")
        val extension = MimeTypeMap.getFileExtensionFromUrl(newFile.toString())
        val document = file.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("tradeLicenseDoc", file.name, document)
        val finalSubmit = finalSubmit.toRequestBody("text/plain".toMediaTypeOrNull())


        api.reUploadCommercialDoc(
            "Bearer ${token}",
            finalSubmit,
            filePart
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }

    fun submitForReview(
        selectedImageUriTrade: File, selectedImageUriComm: File, selectedImageUriBank: File,
        token: String,
        finalSubmit: String,
        onApiCallback: (status: Boolean, message: String?, result: ReUploadResponse?) -> Unit
    ) {


        if (!selectedImageUriTrade.isNull()) {
            val newFile: String = selectedImageUriTrade.toString().replace("\\s".toRegex(), "")
            val extension = MimeTypeMap.getFileExtensionFromUrl(newFile.toString())
            val document =
                selectedImageUriTrade.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
            filePart_trade = MultipartBody.Part.createFormData(
                "tradeLicenseDoc",
                selectedImageUriTrade.name,
                document
            )

        }

        if (!selectedImageUriComm.isNull()) {
            val newFile1: String = selectedImageUriComm.toString().replace("\\s".toRegex(), "")
            val extension1 = MimeTypeMap.getFileExtensionFromUrl(newFile1.toString())
            val document1 =
                selectedImageUriComm.asRequestBody(getMediaType(extension1).toMediaTypeOrNull())
            filePart_commercial = MultipartBody.Part.createFormData(
                "commercialRegistrationDoc",
                selectedImageUriComm.name,
                document1
            )
        }
        if (!selectedImageUriBank.isNull()) {
            val newFile2: String = selectedImageUriBank.toString().replace("\\s".toRegex(), "")
            val extension2 = MimeTypeMap.getFileExtensionFromUrl(newFile2.toString())
            val document2 =
                selectedImageUriBank.asRequestBody(getMediaType(extension2).toMediaTypeOrNull())
            filePart_bank = MultipartBody.Part.createFormData(
                "bankDetailsDoc",
                selectedImageUriBank.name,
                document2
            )
        }


        val finalSubmit = finalSubmit.toRequestBody("text/plain".toMediaTypeOrNull())
        api.submitForReview(
            "Bearer ${token}",
            finalSubmit, filePart_trade, filePart_bank, filePart_commercial
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }

    fun reUploadUserDetails(
        token: String,
        onApiCallback: (status: Boolean, message: String?, result: ReUploadUserDeatilsResponse.Data?) -> Unit
    ) {
        api.reUploadUserDetails(
            token
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                it?.data.let {
                    onApiCallback(true, null, it)
                }

            }
        )
    }
}