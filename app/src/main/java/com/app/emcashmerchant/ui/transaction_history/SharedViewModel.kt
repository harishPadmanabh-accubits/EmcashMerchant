package com.app.emcashmerchant.ui.transaction_history

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.ui.transaction_history.PagingSource.AllTransactionsPagingSource
import com.app.emcashmerchant.utils.extensions.default
import timber.log.Timber

class SharedViewModel(val app:Application) : AndroidViewModel(app) {
    val type = MutableLiveData<String>()
    val status = MutableLiveData<String>().default("4")
    val date = MutableLiveData<ArrayList<String>>()
    val endDate = MutableLiveData<String>()
    val filter = MutableLiveData<HistoryFilter>().default(HistoryFilter())

    fun sendType(s_type: String) {
        type.value = s_type

    }

    fun sendStatus(s_status: String) {
        status.value = s_status
        filter.value = HistoryFilter(status = s_status)

    }

    fun sendDate(s_date: ArrayList<String>) {
        date.value = s_date
        filter.value = HistoryFilter(
            startDate = s_date.first(),
            endDate = s_date.last()
        )
        Timber.e("date start ${s_date[0]} end ${s_date.last()}")
    }

    val pagedTransactions= Transformations.switchMap(filter){
         Pager(PagingConfig(1)) {
            AllTransactionsPagingSource(
                ApiManger(app).api,
                SessionStorage(app).accesToken.toString(),
                filter.value?.mode ?: "0",
                filter.value?.startDate ?: "",
                filter.value?.endDate ?: "",
                filter.value?.status ?: "",
                filter.value?.type ?: ""
            )
        }.liveData.cachedIn(viewModelScope)
    }

    val pagedInboundTransactions= Transformations.switchMap(filter){
        Pager(PagingConfig(1)) {
            AllTransactionsPagingSource(
                ApiManger(app).api,
                SessionStorage(app).accesToken.toString(),
                filter.value?.mode ?: "1",
                filter.value?.startDate ?: "",
                filter.value?.endDate ?: "",
                filter.value?.status ?: "",
                filter.value?.type ?: ""
            )
        }.liveData.cachedIn(viewModelScope)
    }

    val pagedOutboundTransactions= Transformations.switchMap(filter){
        Pager(PagingConfig(1)) {
            AllTransactionsPagingSource(
                ApiManger(app).api,
                SessionStorage(app).accesToken.toString(),
                filter.value?.mode ?: "2",
                filter.value?.startDate ?: "",
                filter.value?.endDate ?: "",
                filter.value?.status ?: "",
                filter.value?.type ?: ""
            )
        }.liveData.cachedIn(viewModelScope)
    }


}





data class HistoryFilter(
    var mode: String = "0",
    var startDate: String = "",
    var endDate: String = "",
    var status: String = "",
    var type: String = ""
)





