package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.data.models.LogOutResponse
import com.app.emcashmerchant.data.models.TransactionHistoryResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class TransactionHistoryRespository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api




    fun getAllGroupedTransactionHistory(
        mode: String, type: String, status: String, startDate: String, endDate: String,
        onApiCallback: (status: Boolean, message: String?, result: GroupedTransactionHistoryResponse.Data?) -> Unit
    ) {
        api.allGroupedTransactionHistoryReponse(
            "Bearer ${sessionStorage.accesToken}",
            1,
            1000,
            mode,
            startDate,
            endDate, status,
            type
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }

    fun getGroupedInboundTransaction(
        mode: String, type: String, status: String, startDate: String, endDate: String,
        onApiCallback: (status: Boolean, message: String?, result: GroupedTransactionHistoryResponse.Data?) -> Unit
    ) {
        api.allGroupedTransactionHistoryReponse(
            "Bearer ${sessionStorage.accesToken}",
            1,
            1000,
            mode,
            startDate,
            endDate, status,
            type
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }





    fun getGroupedOutboundTransaction(
        mode: String, type: String, status: String, startDate: String, endDate: String,
        onApiCallback: (status: Boolean, message: String?, result: GroupedTransactionHistoryResponse.Data?) -> Unit
    ) {
        api.allGroupedTransactionHistoryReponse(
            "Bearer ${sessionStorage.accesToken}",
            1,
            1000,
            mode,
            startDate,
            endDate, status,
            type
        ).awaitResponse(
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