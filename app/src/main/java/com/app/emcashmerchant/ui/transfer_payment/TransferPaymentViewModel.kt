package com.app.emcashmerchant.ui.transfer_payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.CheckQrCodeRequest
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.models.CheckQrCodeResponse
import com.app.emcashmerchant.data.models.TopUpResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.LoadEmcashRepository
import com.app.emcashmerchant.data.network.Repositories.TransferPaymentRepository

class TransferPaymentViewModel(val app: Application) : AndroidViewModel(app) {
    var qrCodeCheckStatus = MutableLiveData<ApiMapper<CheckQrCodeResponse.Data>>()
    val repository = TransferPaymentRepository(app)

    fun checkQr(checkQrCodeRequest: CheckQrCodeRequest) {
        qrCodeCheckStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.QrCodeCheck(checkQrCodeRequest) { status, message, result ->
            when (status) {
                true -> {
                    qrCodeCheckStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    qrCodeCheckStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }
}