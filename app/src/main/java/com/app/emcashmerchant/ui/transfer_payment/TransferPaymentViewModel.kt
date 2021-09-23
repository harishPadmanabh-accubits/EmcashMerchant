package com.app.emcashmerchant.ui.transfer_payment

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.CheckQrCodeRequest
import com.app.emcashmerchant.data.modelrequest.IntiateContactPaymentRequest
import com.app.emcashmerchant.data.modelrequest.RejectAcceptRequest
import com.app.emcashmerchant.data.modelrequest.TransferAmountRequest
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.TransferPaymentRepository
import com.app.emcashmerchant.ui.transaction_history.PagingSource.AllTransactionsPagingSource
import com.app.emcashmerchant.ui.transfer_payment.PagingSource.AllContactsPagingSource
import com.app.emcashmerchant.utils.extensions.default
import timber.log.Timber
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class TransferPaymentViewModel(val app: Application) : AndroidViewModel(app) {
    var qrCodeCheckStatus = MutableLiveData<ApiMapper<CheckQrCodeResponse.Data>>()
    var transferAmountStatus = MutableLiveData<ApiMapper<TransferAmountResponse>>()
    var rejectStatus = MutableLiveData<ApiMapper<RejectResponse>>()
    var acceptStatus = MutableLiveData<ApiMapper<AcceptResponse>>()

    var allContactsStatus = MutableLiveData<ApiMapper<AllContactResponse.Data>>()
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

    fun reject(request: RejectAcceptRequest) {
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

    fun accept(request: RejectAcceptRequest) {
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


    fun getContactsList(search: String) {
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


    fun getGroupContactListViewMode(rows: ArrayList<AllContactResponse.Data.Row>): ArrayList<AllContactResponse.ContactGroupedViewModel> {
        val groupedList =
            ArrayList<AllContactResponse.ContactGroupedViewModel>()  //final processed list
        val accessalphabets = ArrayList<String>() //to check if a date is alreaaady accesssed

        rows?.let { allContacts ->
            if (!allContacts.isNullOrEmpty()) {
                val firstLetter = allContacts.map {
                    it.name                                                            //gets a list of updatedAt from all data using map
                }
                firstLetter.forEach { fullName ->
                    val firstletter = fullName
                    if (accessalphabets.contains(firstletter)) {
                        val groupedActivities = allContacts.filter { row ->
                            fullName == firstletter
                        }
                        groupedList.add(
                            AllContactResponse.ContactGroupedViewModel(
                                firstletter,
                                groupedActivities
                            )
                        )  //add to custom model
                        accessalphabets.add(firstletter)
                    }
                }
            }
        }
        return groupedList //pass this to adapter

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


    fun intiatePayment(intiateContactPaymentRequest: IntiateContactPaymentRequest) {
        intiatePaymentStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.intiatePayment(intiateContactPaymentRequest) { status, message, result ->
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
