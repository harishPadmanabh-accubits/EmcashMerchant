package com.app.emcashmerchant.ui.paymentRequest.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.GenerateQrCodeRequest
import com.app.emcashmerchant.data.model.request.PaymentRequest
import com.app.emcashmerchant.data.model.response.AllContactResponse
import com.app.emcashmerchant.data.model.response.GenerateQrCodeResponse
import com.app.emcashmerchant.data.model.response.PaymentRequestResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.Repositories.RequestPaymentRepository
import com.app.emcashmerchant.ui.transferPayment.PagingSource.AllContactsPagingSource
import com.app.emcashmerchant.utils.extensions.default

class PaymentRequestViewModel(val app: Application) : AndroidViewModel(app) {
    var allContactsStatus = MutableLiveData<ApiMapper<AllContactResponse.Data>>()
    var generateQrCodeStatus = MutableLiveData<ApiMapper<GenerateQrCodeResponse.Data>>()
    var paymentRequestStatus = MutableLiveData<ApiMapper<PaymentRequestResponse.Data>>()

    val repository = RequestPaymentRepository(app)
    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)
    val search = MutableLiveData<String>().default("")
    val pagedContacts = Transformations.switchMap(search){
        Pager(PagingConfig(1)) {
            AllContactsPagingSource(
                api,
                sessionStorage.accesToken.toString(), it
            )
        }.liveData.cachedIn(viewModelScope)
    }





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