package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.response.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class WalletRepository(val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun walletResponse(
        page:Int,limit:Int,
        onApiCallback: (status: Boolean, message: String?, result: WalletTransactionResponse.Data?) -> Unit
    ) {
        api.getWalletTransactions( page,limit).awaitResponse(
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