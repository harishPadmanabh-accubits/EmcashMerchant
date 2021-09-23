package com.app.emcashmerchant.ui.home.home_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.data.models.WalletResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.HomeRepository
import timber.log.Timber

class HomeViewModel(val app: Application) : AndroidViewModel(app) {
    var walletDetails = MutableLiveData<ApiMapper<WalletResponse.Data>>()
    var recentTransactions = MutableLiveData<ApiMapper<RecentTransactionResponse.Data>>()

    val repository = HomeRepository(app)
    var balance: String? = null


    fun getWalletDetails() {
        walletDetails.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getWalletDetails() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    walletDetails.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    walletDetails.value = ApiMapper(ApiCallStatus.ERROR, null, message)

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