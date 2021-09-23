package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.AddBankDetailsRequest
import com.app.emcashmerchant.data.modelrequest.EditBankDetailsRequest
import com.app.emcashmerchant.data.modelrequest.SignupInitialRequestBody
import com.app.emcashmerchant.data.models.AddBankDetailsResponse
import com.app.emcashmerchant.data.models.BankDetailsResponse
import com.app.emcashmerchant.data.models.EditBankDetailsResponse
import com.app.emcashmerchant.data.models.SignupInitialResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class BankRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun addBankDetails(
        addBankDetailsRequest: AddBankDetailsRequest,
        onApiCallback: (status: Boolean, message: String?, result: AddBankDetailsResponse?) -> Unit
    ) {
        api.addBankDetails("Bearer ${sessionStorage.accesToken}",addBankDetailsRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }

    fun editDetails(
        editBankDetailsRequest: EditBankDetailsRequest,
        onApiCallback: (status: Boolean, message: String?, result: EditBankDetailsResponse?) -> Unit
    ) {
        api.editBankDetails("Bearer ${sessionStorage.accesToken}",editBankDetailsRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }


    fun getBankDetails(
        onApiCallback: (status: Boolean, message: String?, result: BankDetailsResponse.Data?) -> Unit
    ) {
        api.getBankDetails("Bearer ${sessionStorage.accesToken}").awaitResponse(
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