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
    var alltransactionHistoryResponse = MutableLiveData<ApiMapper<TransactionHistoryResponse.Data>>()
    var inBoundtransactionHistoryResponse = MutableLiveData<ApiMapper<TransactionHistoryResponse.Data>>()
    var outBoundtransactionHistoryResponse = MutableLiveData<ApiMapper<TransactionHistoryResponse.Data>>()

    val repository = TransactionHistoryRespository(app)

    fun getAllTransactions(mode:String) {
        alltransactionHistoryResponse.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getAllTransactionHistory(mode) { status, message, result ->
            when (status) {
                true -> {
                    alltransactionHistoryResponse.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    alltransactionHistoryResponse.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun getInBoundTransactions(mode:String) {
        inBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getInboundTransaction(mode) { status, message, result ->
            when (status) {
                true -> {
                    inBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    inBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun getOutBoundTransactions(mode:String) {
        outBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getOutboundTransaction(mode) { status, message, result ->
            when (status) {
                true -> {
                    outBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    outBoundtransactionHistoryResponse.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun groupTransactionActivitiesByDate(rows: ArrayList<TransactionHistoryResponse.Data.Row>?): ArrayList<TransactionHistoryGroupViewModel> {

        val finalActivityList = ArrayList<TransactionHistoryGroupViewModel>()  //final processed list
        val accessedDates = ArrayList<String>() //to check if a date is already accesssed

        rows?.let { allTransactions ->   //check if rows are null
            if (!allTransactions.isNullOrEmpty()) {
                val dates = allTransactions.map {
                    it.updatedAt                                                                 //gets a list of updatedAt from all data using map
                }
                dates.forEach { unformattedDate ->
                    Timber.e("Unformatted date $unformattedDate")
                    val formattedDate = dateFormat(unformattedDate)
                    Timber.e("formatted date $formattedDate")

                    if(!accessedDates.contains(formattedDate)){              //check if date already accessed otherwise duplications will occur
                        val groupedActivities = allTransactions.filter { row ->
                            dateFormat(row.updatedAt) == formattedDate
                        }                                                     // get all trnasaction under each item in dates list
                        finalActivityList.add(TransactionHistoryGroupViewModel(formattedDate, groupedActivities))  //add to custom model
                        accessedDates.add(formattedDate)               //add date to accessDate Array
                    }


                }
            }


        }
        return finalActivityList //pass this to adapter
    }

}