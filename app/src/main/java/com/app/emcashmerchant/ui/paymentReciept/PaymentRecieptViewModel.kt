package com.app.emcashmerchant.ui.paymentReciept

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.RecieptRequest
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.models.PaymentReceiptResponse
import com.app.emcashmerchant.data.models.RecieptResponse
import com.app.emcashmerchant.data.models.TopUpResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.LoadEmcashRepository
import com.app.emcashmerchant.data.network.Repositories.PaymentReceiptRepository

class PaymentRecieptViewModel(val app: Application): AndroidViewModel(app)  {

    var paymentReceiptStatus = MutableLiveData<ApiMapper<PaymentReceiptResponse.Data>>()
    var generateReceiptStatus = MutableLiveData<ApiMapper<RecieptResponse>>()

    val repository = PaymentReceiptRepository(app)
     var userId: String = ""

    fun paymentReceipt(reference_id:String) {
        paymentReceiptStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getReceipt(reference_id) { status, message, result ->
            when (status) {
                true -> {
                    paymentReceiptStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    paymentReceiptStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun generateReceipt(recieptRequest: RecieptRequest) {
        generateReceiptStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.generateReciept(recieptRequest) { status, message, result ->
            when (status) {
                true -> {
                    generateReceiptStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    generateReceiptStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

}