package com.app.emcashmerchant.ui.payment_request

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.GenerateQrCodeRequest
import com.app.emcashmerchant.data.modelrequest.PaymentRequest
import com.app.emcashmerchant.data.models.AllContactResponse
import com.app.emcashmerchant.data.models.GenerateQrCodeResponse
import com.app.emcashmerchant.data.models.GroupedContactsResponse
import com.app.emcashmerchant.data.models.PaymentRequestResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.RequestPaymentRepository
import com.app.emcashmerchant.data.network.Repositories.TransferPaymentRepository
import com.app.emcashmerchant.ui.transfer_payment.PagingSource.AllContactsPagingSource

class PaymentRequestViewModel(val app: Application) : AndroidViewModel(app) {
    var allContactsStatus = MutableLiveData<ApiMapper<AllContactResponse.Data>>()
    var generateQrCodeStatus = MutableLiveData<ApiMapper<GenerateQrCodeResponse.Data>>()
    var paymentRequestStatus = MutableLiveData<ApiMapper<PaymentRequestResponse.Data>>()

    val repository = RequestPaymentRepository(app)
    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)


    fun getContactsData(
        search: String
    ): kotlinx.coroutines.flow.Flow<PagingData<GroupedContactsResponse.Data.Row>> {
        return Pager(PagingConfig(10)) {
            AllContactsPagingSource(
                api,
                sessionStorage.accesToken.toString(), search
            )
        }.flow.cachedIn(viewModelScope)
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