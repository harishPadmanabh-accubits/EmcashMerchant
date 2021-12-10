package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.AddBankDetailsRequest
import com.app.emcashmerchant.data.model.request.EditBankDetailsRequest
import com.app.emcashmerchant.data.model.response.AddBankDetailsResponse
import com.app.emcashmerchant.data.model.response.BankDetailsResponse
import com.app.emcashmerchant.data.model.response.EditBankDetailsResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class BankRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun addBankDetails(
        addBankDetailsRequest: AddBankDetailsRequest,
        onApiCallback: (status: Boolean, message: String?, result: AddBankDetailsResponse?) -> Unit
    ) {
        api.addBankDetails(addBankDetailsRequest)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)
                }, onSuccess = { data ->
                    data?.let {
                        onApiCallback(true, null, data)
                    }
                })
    }

    fun editDetails(
        editBankDetailsRequest: EditBankDetailsRequest,
        onApiCallback: (status: Boolean, message: String?, result: EditBankDetailsResponse?) -> Unit
    ) {
        api.editBankDetails(editBankDetailsRequest)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)
                }, onSuccess = { data ->
                    data?.let {
                        onApiCallback(true, null, data)
                    }
                })
    }


    fun getBankDetails(
        onApiCallback: (status: Boolean, message: String?, result: BankDetailsResponse.Data?) -> Unit
    ) {
        api.getBankDetails().awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }


}