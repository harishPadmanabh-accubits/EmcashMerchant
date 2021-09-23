package com.app.emcashmerchant.ui.viewAllTransaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.AllTransactionRespository
import com.app.emcashmerchant.ui.viewAllTransaction.pagingSource.ViewAllTransactionPagingSource
import com.app.emcashmerchant.ui.wallet.PagingSource.WalletTransactionPagingSource
import timber.log.Timber

class AllTransactionsViewModel (val app: Application) : AndroidViewModel(app) {
    var allTransactions = MutableLiveData<ApiMapper<RecentTransactionResponse.Data>>()
    val repository = AllTransactionRespository(app)
    var allTransactionsStatus = MutableLiveData<ApiMapper<RecentTransactionResponse.Data>>()
    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)

    val viewAllActivities = Pager(PagingConfig(1)){
        ViewAllTransactionPagingSource(api,sessionStorage.accesToken.toString())
    }.flow.cachedIn(viewModelScope)

    fun getAllTransactions() {
        allTransactions.value = ApiMapper(ApiCallStatus.LOADING, null, null)
        repository.getAllTransactions() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    allTransactions.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    allTransactions.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


}