package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.*
import com.app.emcashmerchant.data.model.response.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class RequestPaymentRepository(val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api


    fun getAllContacts(
        search: String,
        onApiCallback: (status: Boolean, message: String?, result: AllContactResponse.Data?) -> Unit

    ) {
        api.getAllContacts(1, 100, search)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)

                }, onSuccess = {
                    it?.data.let {
                        onApiCallback(true, null, it)
                    }
                }
            )
    }


    fun generateQrCodeRequest(
        generateQrCodeRequest: GenerateQrCodeRequest,
        onApiCallback: (status: Boolean, message: String?, result: GenerateQrCodeResponse.Data?) -> Unit

    ) {
        api.generateQrCodeRequest(generateQrCodeRequest)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)

                }, onSuccess = {
                    it?.data.let {
                        onApiCallback(true, null, it)
                    }
                }
            )
    }

    fun paymentRequest(
        paymentRequest: PaymentRequest,
        onApiCallback: (status: Boolean, message: String?, result: PaymentRequestResponse.Data?) -> Unit

    ) {
        api.requestPayment(paymentRequest)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)

                }, onSuccess = {
                    it?.data.let {
                        onApiCallback(true, null, it)
                    }
                }
            )
    }
}