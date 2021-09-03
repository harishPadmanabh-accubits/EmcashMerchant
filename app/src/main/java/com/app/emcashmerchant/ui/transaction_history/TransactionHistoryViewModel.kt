package com.app.emcashmerchant.ui.transaction_history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.TransactionHistoryRespository
import com.app.emcashmerchant.data.network.Repositories.WalletRepository
import com.app.emcashmerchant.ui.transaction_history.PagingSource.AllTransactionsPagingSource
import com.app.emcashmerchant.ui.wallet.PagingSource.WalletTransactionPagingSource
import com.app.emcashmerchant.utils.extensions.dateFormat
import kotlinx.coroutines.flow.onStart
import org.w3c.dom.CharacterData
import timber.log.Timber
import java.util.concurrent.Flow


class TransactionHistoryViewModel(val app: Application) : AndroidViewModel(app) {

    val repository = TransactionHistoryRespository(app)
    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)


    fun getListData(
        mode: String,
        startDate: String,
        endDate: String,
        status: String,
        type: String
    ): kotlinx.coroutines.flow.Flow<PagingData<GroupedTransactionHistoryResponse.Data.Row>> {
        return Pager(PagingConfig(10)) {
            AllTransactionsPagingSource(
                api,
                sessionStorage.accesToken.toString(),
                mode, startDate,
                endDate,
                status,
                type
            )
        }.flow.cachedIn(viewModelScope)
    }

    val allTransactions = Pager(PagingConfig(10)) {
        AllTransactionsPagingSource(api, sessionStorage.accesToken.toString(), "0", "", "", "", "")
    }.flow.cachedIn(viewModelScope)

    val inBoundTransactions = Pager(PagingConfig(10)) {
        AllTransactionsPagingSource(api, sessionStorage.accesToken.toString(), "1", "", "", "", "")
    }.flow.cachedIn(viewModelScope)

    val outBoundTransactions = Pager(PagingConfig(10)) {
        AllTransactionsPagingSource(api, sessionStorage.accesToken.toString(), "2", "", "", "", "")
    }.flow.cachedIn(viewModelScope)
}
