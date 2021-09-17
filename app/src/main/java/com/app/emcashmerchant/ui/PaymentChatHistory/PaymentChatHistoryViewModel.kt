package com.app.emcashmerchant.ui.PaymentChatHistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.PaymentChatRepository
import com.app.emcashmerchant.ui.PaymentChatHistory.PagingSource.ChatPagingSource
import com.app.emcashmerchant.utils.extensions.default

class PaymentChatHistoryViewModel(val app: Application) : AndroidViewModel(app) {

    var paymentChatStatus = MutableLiveData<ApiMapper<GroupedChatHistoryResponse.Data>>()
    var blockStatus = MutableLiveData<ApiMapper<BlockedResponse>>()
    var unblockStatus = MutableLiveData<ApiMapper<UnblockedResponse>>()

    val repository = PaymentChatRepository(app)
    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)
    var userId = 0


    val _refreshChat  = MutableLiveData<Boolean>()


    val pagedHistoryItems = Transformations.switchMap(_refreshChat){
        Pager(PagingConfig(pageSize = 1)) {
            ChatPagingSource(
                api,
                sessionStorage.accesToken.toString(),userId.toString()
            )
        }.liveData.cachedIn(viewModelScope)
    }

    fun getListData(userId: Int): kotlinx.coroutines.flow.Flow<PagingData<GroupedChatHistoryResponse.Data.Row>> {
        return Pager(PagingConfig(10)) {
            ChatPagingSource(
                api,
                sessionStorage.accesToken.toString(),userId.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun block(userId: Int) {
        blockStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.blockContact(userId) { status, message, result ->
            when (status) {
                true -> {
                    blockStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    blockStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun unBlock(userId: Int) {
        unblockStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.unBlockContact(userId) { status, message, result ->
            when (status) {
                true -> {
                    unblockStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    unblockStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun getPaymentChat(userId: Int) {
        paymentChatStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.paymentChat(userId) { status, message, result ->
            when (status) {
                true -> {
                    paymentChatStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    paymentChatStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }
//
//
//    fun groupPaymentTransactionsByDate(rows: ArrayList<PaymentChatResponse.Data.Row>?): ArrayList<PaymentChatResponse.ChatTransactionViewModel> {
//
//        val paymentActivityList = ArrayList<PaymentChatResponse.ChatTransactionViewModel>()  //final processed list
//        val accessedDates = ArrayList<String>() //to check if a date is alreaaady accesssed
//
//        rows?.let { allTransactions ->   //check if rows are null
//            if (!allTransactions.isNullOrEmpty()) {
//                val dates = allTransactions.map {
//                    it.updatedAt                                                                 //gets a list of updatedAt from all data using map
//                }
//                dates.forEach { unformattedDate ->
//                    Timber.e("Unformatted date $unformattedDate")
//                    val formattedDate = dateFormat(unformattedDate)
//                    Timber.e("formatted date $formattedDate")
//
//                    if(!accessedDates.contains(formattedDate)){              //check if date already accessed otherwise duplications will occur
//                        val groupedActivities = allTransactions.filter { row ->
//                            dateFormat(row.updatedAt) == formattedDate
//                        }                                                     // get all trnasaction under each item in dates list
//                        paymentActivityList.add(
//                            PaymentChatResponse.ChatTransactionViewModel(
//                                formattedDate,
//                                groupedActivities
//                            )
//                        )  //add to custom model
//                        accessedDates.add(formattedDate)               //add date to accessDate Array
//                    }
//
//
//                }
//            }
//
//
//        }
//        return paymentActivityList //pass this to adapter
//    }


}