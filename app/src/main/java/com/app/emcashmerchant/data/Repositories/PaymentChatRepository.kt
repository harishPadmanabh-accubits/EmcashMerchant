package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.response.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class PaymentChatRepository(val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun paymentChat(
        userId: Int,
        onApiCallback: (status: Boolean, message: String?, result: GroupedChatHistoryResponse.Data?) -> Unit
    ) {
        api.getGroupedChatResponse(userId, 1, 10).awaitResponse(
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

    fun blockContact(
        userId: Int,
        onApiCallback: (status: Boolean, message: String?, result: BlockedResponse?) -> Unit
    ) {
        api.blockContact(userId).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = { data ->
                data.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }

    fun unBlockContact(
        userId: Int,
        onApiCallback: (status: Boolean, message: String?, result: UnblockedResponse?) -> Unit
    ) {
        api.unBlockContact(userId).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = { data ->
                data.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }


}