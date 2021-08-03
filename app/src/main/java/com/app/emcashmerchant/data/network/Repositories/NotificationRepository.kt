package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.LoginResquestBody
import com.app.emcashmerchant.data.models.LoginResponse
import com.app.emcashmerchant.data.models.NotificationResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class NotificationRepository (val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api
    fun notifications(
        page:Int,limit:Int,
        onApiCallback: (status: Boolean, message: String?, result: NotificationResponse.Data?) -> Unit
    ) {

        api.notification("Bearer ${sessionStorage.accesToken}",page,limit).awaitResponse(
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
}