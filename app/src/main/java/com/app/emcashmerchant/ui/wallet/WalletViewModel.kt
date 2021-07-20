package com.app.emcashmerchant.ui.wallet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.Data
import com.app.emcashmerchant.data.models.WalletActivityModel
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.WalletRepository
import com.app.emcashmerchant.utils.extensions.dateFormat
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


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

    fun groupActivitiesByDate(rows: ArrayList<WalletTransactionResponse.Data.Row>?): ArrayList<WalletActivityModel> {

        val finalActivityList = ArrayList<WalletActivityModel>()  //final processed list
        val accessedDates = ArrayList<String>() //to check if a date is alreaaady accesssed

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
                        finalActivityList.add(WalletActivityModel(formattedDate, groupedActivities))  //add to custom model
                        accessedDates.add(formattedDate)               //add date to accessDate Array
                    }


                }
            }


        }
        return finalActivityList //pass this to adapter
    }


}
