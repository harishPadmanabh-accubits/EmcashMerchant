package com.app.emcashmerchant.ui.transferPayment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.CheckQrCodeRequest
import com.app.emcashmerchant.data.model.request.InitiateContactPaymentRequest
import com.app.emcashmerchant.data.model.request.RejectAcceptRequest
import com.app.emcashmerchant.data.model.request.TransferAmountRequest
import com.app.emcashmerchant.data.model.response.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.Repositories.TransferPaymentRepository
import com.app.emcashmerchant.ui.transferPayment.PagingSource.AllContactsPagingSource
import com.app.emcashmerchant.utils.extensions.default
import timber.log.Timber


class TransferPaymentViewModel(val app: Application) : AndroidViewModel(app) {
    var qrCodeCheckStatus = MutableLiveData<ApiMapper<CheckQrCodeResponse.Data>>()
    var transferAmountStatus = MutableLiveData<ApiMapper<TransferAmountResponse>>()
    var rejectStatus = MutableLiveData<ApiMapper<RejectResponse>>()
    var acceptStatus = MutableLiveData<ApiMapper<AcceptResponse>>()

    var oneContactStatus = MutableLiveData<ApiMapper<CustomerContactResponse.Data>>()
    var intiatePaymentStatus = MutableLiveData<ApiMapper<IntiateContactPaymentResponse.Data>>()
    var recentTransactions = MutableLiveData<ApiMapper<RecentTransactionResponse.Data>>()

    val repository = TransferPaymentRepository(app)
    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)
    private var screen: String? = null


    val search = MutableLiveData<String>().default("")
    val pagedContacts = Transformations.switchMap(search) {
        Pager(PagingConfig(1)) {
            AllContactsPagingSource(
                api,
                sessionStorage.accesToken.toString(), it
            )
        }.liveData.cachedIn(viewModelScope)
    }


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

    fun TransferAmount(transferAmountRequest: TransferAmountRequest) {
        transferAmountStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.performTransferAmount(transferAmountRequest) { status, message, result ->
            when (status) {
                true -> {
                    transferAmountStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    transferAmountStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun rejectPayment(request: RejectAcceptRequest) {
        rejectStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.performReject(request) { status, message, result ->
            when (status) {
                true -> {
                    rejectStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    rejectStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun acceptPayment(request: RejectAcceptRequest) {
        acceptStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.performAccept(request) { status, message, result ->
            when (status) {
                true -> {
                    acceptStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    acceptStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }





    fun getOneContact(userid: String) {
        oneContactStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getOneContact(userid) { status, message, result ->
            when (status) {
                true -> {
                    oneContactStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    oneContactStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun initiatePayment(initiateContactPaymentRequest: InitiateContactPaymentRequest) {
        intiatePaymentStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.intiatePayment(initiateContactPaymentRequest) { status, message, result ->
            when (status) {
                true -> {
                    intiatePaymentStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    intiatePaymentStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun getRecentTransactions() {
        recentTransactions.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getRecentTransactions() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    recentTransactions.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    recentTransactions.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

}

enum class Screens {
    TRANSFER_CONTACT_LIST,
    CHAT_SCREEN
}
