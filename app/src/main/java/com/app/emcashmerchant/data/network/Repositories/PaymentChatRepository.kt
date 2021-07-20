package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.PaymentChatResponse
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class PaymentChatRepository (val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun paymentChat(userId:Int,
        onApiCallback: (status: Boolean, message: String?, result: PaymentChatResponse.Data?) -> Unit
    ) {
        api.getChatResponse( "Bearer ${sessionStorage.accesToken}",userId,1,20).awaitResponse(
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