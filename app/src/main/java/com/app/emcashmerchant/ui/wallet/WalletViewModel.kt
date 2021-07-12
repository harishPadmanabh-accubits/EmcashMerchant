package com.app.emcashmerchant.ui.wallet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.WalletRepository


class WalletViewModel(val app: Application) : AndroidViewModel(app) {
    var walletTransactionStatus = MutableLiveData<ApiMapper<WalletTransactionResponse.Data>>()
    val repository = WalletRepository(app)

    fun walletTransactions() {
        walletTransactionStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.walletResponse() { status, message, result ->
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
