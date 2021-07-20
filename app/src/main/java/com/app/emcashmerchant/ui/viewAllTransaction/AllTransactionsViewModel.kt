package com.app.emcashmerchant.ui.viewAllTransaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.AllTransactionRespository
import timber.log.Timber

class AllTransactionsViewModel (val app: Application) : AndroidViewModel(app) {
    var allTransactions = MutableLiveData<ApiMapper<RecentTransactionResponse.Data>>()
    val repository = AllTransactionRespository(app)


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