package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.WithDrawRequest
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class WalletRepository(val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun walletResponse(
        onApiCallback: (status: Boolean, message: String?, result: WalletTransactionResponse.Data?) -> Unit
    ) {
        api.walletTransactionResponse( "Bearer ${sessionStorage.accesToken}",1,10).awaitResponse(
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