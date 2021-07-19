package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.CheckQrCodeRequest
import com.app.emcashmerchant.data.models.CheckQrCodeResponse
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class TransferPaymentRepository(val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun QrCodeCheck(
        qrCodeRequest: CheckQrCodeRequest,onApiCallback: (status: Boolean, message: String?, result: CheckQrCodeResponse.Data?) -> Unit
    ) {
        api.qrCodeCheck( qrCodeRequest,"Bearer ${sessionStorage.accesToken}").awaitResponse(
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