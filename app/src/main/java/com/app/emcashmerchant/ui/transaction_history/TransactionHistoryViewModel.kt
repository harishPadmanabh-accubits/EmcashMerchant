package com.app.emcashmerchant.ui.transaction_history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.TransactionHistoryRespository
import com.app.emcashmerchant.data.network.Repositories.WalletRepository
import com.app.emcashmerchant.utils.extensions.dateFormat
import timber.log.Timber


class TransactionHistoryViewModel(val app: Application) : AndroidViewModel(app) {

    var allGroupedtransactionHistoryResponse =
        MutableLiveData<ApiMapper<GroupedTransactionHistoryResponse.Data>>()
    var groupedInBoundtransactionHistoryResponse =
        MutableLiveData<ApiMapper<GroupedTransactionHistoryResponse.Data>>()
    var groupedOutBoundtransactionHistoryResponse =
        MutableLiveData<ApiMapper<GroupedTransactionHistoryResponse.Data>>()

    val repository = TransactionHistoryRespository(app)




    fun getAllGroupedTransactions(
        mode: String,
        type: String,
        status: String,
        startDate: String,
        endDate: String
    ) {
        allGroupedtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getAllGroupedTransactionHistory(
            mode,
            type,
            status,
            startDate,
            endDate
        ) { status, message, result ->
            when (status) {
                true -> {
                    allGroupedtransactionHistoryResponse.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    allGroupedtransactionHistoryResponse.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun getGroupedInBoundTransactions(
        mode: String,
        type: String,
        status: String,
        startDate: String,
        endDate: String
    ) {
        groupedInBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getGroupedInboundTransaction(
            mode,
            type,
            status,
            startDate,
            endDate
        ) { status, message, result ->
            when (status) {
                true -> {
                    groupedInBoundtransactionHistoryResponse.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    groupedInBoundtransactionHistoryResponse.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun getGroupedOutBoundTransactions(
        mode: String,
        type: String,
        status: String,
        startDate: String,
        endDate: String
    ) {
        groupedOutBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getGroupedOutboundTransaction(
            mode,
            type,
            status,
            startDate,
            endDate
        ) { status, message, result ->
            when (status) {
                true -> {
                    groupedOutBoundtransactionHistoryResponse.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    groupedOutBoundtransactionHistoryResponse.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


}