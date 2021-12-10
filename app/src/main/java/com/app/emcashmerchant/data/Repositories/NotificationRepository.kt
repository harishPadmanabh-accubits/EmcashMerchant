package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.response.NotificationClickResponse
import com.app.emcashmerchant.data.model.response.NotificationResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class NotificationRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api
    fun notifications(
        page: Int, limit: Int,
        onApiCallback: (status: Boolean, message: String?, result: NotificationResponse.Data?) -> Unit
    ) {

        api.getNotifications(page, limit).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    onApiCallback(true, null, data)
                }
            }
        )


    }


    fun onNotificationItemClick(
        id: String,
        onApiCallback: (status: Boolean, message: String?, result: NotificationClickResponse.Data?) -> Unit
    ) {
        api.onNotificationItemClick(id).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = { it ->
                it?.data?.let { data ->
                    onApiCallback(true, null, data)
                }
            }
        )


    }

}