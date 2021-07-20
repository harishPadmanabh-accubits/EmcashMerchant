package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class AllTransactionRespository (val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api


    fun getAllTransactions(onApiCallback: (status: Boolean, message: String?, result: RecentTransactionResponse.Data?) -> Unit) {
        api.recentTransaction("Bearer ${sessionStorage.accesToken}", 1, 100).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var data = it?.data
                data.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }
}