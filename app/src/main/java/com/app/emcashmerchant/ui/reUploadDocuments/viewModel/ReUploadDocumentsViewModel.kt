package com.app.emcashmerchant.ui.reUploadDocuments.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.ReUploadDocumentsRepository
import java.io.File

class ReUploadDocumentsViewModel(val app: Application) : AndroidViewModel(app) {

    val repository = ReUploadDocumentsRepository(app)
    var reUploadBankDocument = MutableLiveData<ApiMapper<ReUploadResponse>>()
    var reUploadCommercialDocument = MutableLiveData<ApiMapper<ReUploadResponse>>()
    var reUploadTradeLicenseDocument = MutableLiveData<ApiMapper<ReUploadResponse>>()
    var submitForReview = MutableLiveData<ApiMapper<ReUploadResponse>>()
    var reUploadUserDetailsStatus = MutableLiveData<ApiMapper<ReUploadUserDeatilsResponse.Data>>()

    fun reUploadBankDetailsDoc(token:String,file: File, finalSubmit: String) {
        reUploadBankDocument.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.reUploadBankDetailsDoc(token,file, finalSubmit) { status, message, result ->
            when (status) {
                true -> {
                    reUploadBankDocument.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    reUploadBankDocument.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun reUploadCommercialDoc(token:String,file: File, finalSubmit: String) {
        reUploadCommercialDocument.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.reUploadCommercialDoc(token,file, finalSubmit) { status, message, result ->
            when (status) {
                true -> {
                    reUploadCommercialDocument.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    reUploadCommercialDocument.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun reUploadTradeLicenseDoc(token:String,file: File, finalSubmit: String) {
        reUploadTradeLicenseDocument.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.reUploadTradeLicenseDoc(token,file, finalSubmit) { status, message, result ->
            when (status) {
                true -> {
                    reUploadTradeLicenseDocument.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    reUploadTradeLicenseDocument.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun submitForReview(selectedImageUriTrade:File,selectedImageUriComm:File,selectedImageUriBank:File,token:String,finalSubmit: String) {

        submitForReview.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.submitForReview(selectedImageUriTrade,selectedImageUriComm,selectedImageUriBank,token,finalSubmit) { status, message, result ->
            when (status) {
                true -> {
                    submitForReview.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    submitForReview.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun reUploadUserDetails(token: String) {
        reUploadUserDetailsStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.reUploadUserDetails(token) { status, message, result ->
            when (status) {
                true -> {
                    reUploadUserDetailsStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    reUploadUserDetailsStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }
}