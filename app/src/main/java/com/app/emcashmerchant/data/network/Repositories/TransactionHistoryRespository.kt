package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.LogOutResponse
import com.app.emcashmerchant.data.models.TransactionHistoryResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class TransactionHistoryRespository (val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun getAllTransactionHistory(
        mode:String,
        onApiCallback: (status: Boolean, message: String?, result: TransactionHistoryResponse.Data?) -> Unit
    ) {
        api.allTransactionHistoryReponse("Bearer ${sessionStorage.accesToken}",1,1000,mode,"","").awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }

    fun getInboundTransaction(
        mode:String,
        onApiCallback: (status: Boolean, message: String?, result: TransactionHistoryResponse.Data?) -> Unit
    ) {
        api.allTransactionHistoryReponse("Bearer ${sessionStorage.accesToken}",1,1000,mode,"","").awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }

    fun getOutboundTransaction(
        mode:String,
        onApiCallback: (status: Boolean, message: String?, result: TransactionHistoryResponse.Data?) -> Unit
    ) {
        api.allTransactionHistoryReponse("Bearer ${sessionStorage.accesToken}",1,1000,mode,"","").awaitResponse(
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