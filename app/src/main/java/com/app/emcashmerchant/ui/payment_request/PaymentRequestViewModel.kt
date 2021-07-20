package com.app.emcashmerchant.ui.payment_request

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.GenerateQrCodeRequest
import com.app.emcashmerchant.data.modelrequest.PaymentRequest
import com.app.emcashmerchant.data.models.AllContactResponse
import com.app.emcashmerchant.data.models.GenerateQrCodeResponse
import com.app.emcashmerchant.data.models.PaymentRequestResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.RequestPaymentRepository
import com.app.emcashmerchant.data.network.Repositories.TransferPaymentRepository

class PaymentRequestViewModel(val app: Application) : AndroidViewModel(app) {
    var allContactsStatus = MutableLiveData<ApiMapper<AllContactResponse.Data>>()
    var generateQrCodeStatus = MutableLiveData<ApiMapper<GenerateQrCodeResponse.Data>>()
    var paymentRequestStatus = MutableLiveData<ApiMapper<PaymentRequestResponse.Data>>()

    val repository = RequestPaymentRepository(app)

    fun getContactsList(search:String) {
        allContactsStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getAllContacts(search) { status, message, result ->
            when (status) {
                true -> {
                    allContactsStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    allContactsStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun generateQrCode(generateQrCodeRequest: GenerateQrCodeRequest) {
        generateQrCodeStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.generateQrCodeRequest(generateQrCodeRequest) { status, message, result ->
            when (status) {
                true -> {
                    generateQrCodeStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    generateQrCodeStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun paymentRequest(paymentRequest: PaymentRequest) {
        paymentRequestStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.paymentRequest(paymentRequest) { status, message, result ->
            when (status) {
                true -> {
                    paymentRequestStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    paymentRequestStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }
}