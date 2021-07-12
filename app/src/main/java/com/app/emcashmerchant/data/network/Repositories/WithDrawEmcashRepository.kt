package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.modelrequest.WithDrawRequest
import com.app.emcashmerchant.data.models.TopUpResponse
import com.app.emcashmerchant.data.models.WithDrawResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class WithDrawEmcashRepository(val context: Context) {


    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api


    fun withDraw(
    withDrawRequest: WithDrawRequest,
    onApiCallback: (status: Boolean, message: String?, result: WithDrawResponse?) -> Unit
) {
    api.withDraw(withDrawRequest, "Bearer ${sessionStorage.accesToken}").awaitResponse(
        onFailure = {
            onApiCallback(false, it, null)

        }, onSuccess = {
            var data = it
            data.let {
                onApiCallback(true, null, data)

            }
        }
    )
}


}