package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.WithDrawRequest
import com.app.emcashmerchant.data.model.response.BankDetailsResponse
import com.app.emcashmerchant.data.model.response.WithDrawResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class WithDrawEmcashRepository(val context: Context) {


    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api


    fun withDraw(
    withDrawRequest: WithDrawRequest,
    onApiCallback: (status: Boolean, message: String?, result: WithDrawResponse?) -> Unit
) {
    api.withDrawEmCash(withDrawRequest).awaitResponse(
        onFailure = {
            onApiCallback(false, it, null)

        }, onSuccess = {data ->
            data.let {
                onApiCallback(true, null, data)

            }
        }
    )
}
    fun getBankDetails(
        onApiCallback: (status: Boolean, message: String?, result: BankDetailsResponse?) -> Unit
    ) {
        api.getBankDetails().awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {data ->
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }



}