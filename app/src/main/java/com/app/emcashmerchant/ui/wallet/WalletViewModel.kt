package com.app.emcashmerchant.ui.wallet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.GroupedWalletTransactionResponse
import com.app.emcashmerchant.data.models.WalletActivityModel
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.ApiServices
import com.app.emcashmerchant.data.network.Repositories.WalletRepository
import com.app.emcashmerchant.ui.wallet.PagingSource.WalletTransactionPagingSource
import com.app.emcashmerchant.utils.extensions.dateFormat
import timber.log.Timber


class WalletViewModel(val app: Application) : AndroidViewModel(app) {
    var walletTransactionStatus = MutableLiveData<ApiMapper<WalletTransactionResponse.Data>>()
    var groupedWalletTransactionStatus = MutableLiveData<ApiMapper<GroupedWalletTransactionResponse.Data>>()

    val repository = WalletRepository(app)
    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)

    val walletActivities = Pager(PagingConfig(10)){
        WalletTransactionPagingSource(api,sessionStorage.accesToken.toString())
    }.flow.cachedIn(viewModelScope)


    fun walletTransactions(page:Int,limit:Int) {
        walletTransactionStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.walletResponse(page,limit) { status, message, result ->
            when (status) {
                true -> {
                    walletTransactionStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    walletTransactionStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


}
