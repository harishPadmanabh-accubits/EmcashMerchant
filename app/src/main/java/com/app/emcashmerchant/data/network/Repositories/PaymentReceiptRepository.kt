package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.RecieptRequest
import com.app.emcashmerchant.data.models.PaymentReceiptResponse
import com.app.emcashmerchant.data.models.RecieptResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class PaymentReceiptRepository (val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api


    fun getReceipt(
        reference_id:String,
        onApiCallback: (status: Boolean, message: String?, result: PaymentReceiptResponse.Data?) -> Unit
    ) {

        api.paymentReceiptResponse(reference_id,"Bearer ${sessionStorage.accesToken}").awaitResponse(
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

    fun generateReciept(
        receiptRequest: RecieptRequest,
        onApiCallback: (status: Boolean, message: String?, result: RecieptResponse?) -> Unit
    ) {

        api.generateReciept("Bearer ${sessionStorage.accesToken}",receiptRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {data ->
                data?.let {
                    onApiCallback(true, null, data)
                }
            }
        )
    }


}