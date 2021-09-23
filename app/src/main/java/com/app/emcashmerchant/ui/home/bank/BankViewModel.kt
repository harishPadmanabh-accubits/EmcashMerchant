package com.app.emcashmerchant.ui.home.bank

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.AddBankDetailsRequest
import com.app.emcashmerchant.data.modelrequest.EditBankDetailsRequest
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.BankRepository
import timber.log.Timber

class BankViewModel(val app: Application) : AndroidViewModel(app) {

    var addBankDetailsStatus = MutableLiveData<ApiMapper<AddBankDetailsResponse>>()
    var bankDetailsStatus = MutableLiveData<ApiMapper<BankDetailsResponse.Data>>()
    var editBankDetailsStatus = MutableLiveData<ApiMapper<EditBankDetailsResponse>>()


    var uuid:String? = ""
    val repository =
        BankRepository(app)

    fun addBankDetails(
        addBankDetailsRequest: AddBankDetailsRequest
    ) {
        addBankDetailsStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.addBankDetails(addBankDetailsRequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    addBankDetailsStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    addBankDetailsStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun editBankDetails(
        editBankDetailsRequest: EditBankDetailsRequest
    ) {
        editBankDetailsStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.editDetails(editBankDetailsRequest) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    editBankDetailsStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    editBankDetailsStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun bankDetails(
    ) {
        bankDetailsStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getBankDetails() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    bankDetailsStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    bankDetailsStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

}