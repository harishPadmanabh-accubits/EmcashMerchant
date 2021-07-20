package com.app.emcashmerchant.ui.PaymentChatHistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.WithDrawRequest
import com.app.emcashmerchant.data.models.PaymentChatResponse
import com.app.emcashmerchant.data.models.WalletActivityModel
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.models.WithDrawResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.PaymentChatRepository
import com.app.emcashmerchant.data.network.Repositories.WithDrawEmcashRepository
import com.app.emcashmerchant.utils.extensions.dateFormat
import timber.log.Timber

class PaymentChatHistoryViewModel(val app: Application) : AndroidViewModel(app)  {

    var paymentChatStatus = MutableLiveData<ApiMapper<PaymentChatResponse.Data>>()
    val repository = PaymentChatRepository(app)

    fun getPaymentChat(userId:Int) {
        paymentChatStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.paymentChat(userId) { status, message, result ->
            when (status) {
                true -> {
                    paymentChatStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    paymentChatStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun groupPaymentTransactionsByDate(rows: ArrayList<PaymentChatResponse.Data.Row>?): ArrayList<PaymentChatResponse.ChatTransactionViewModel> {

        val paymentActivityList = ArrayList<PaymentChatResponse.ChatTransactionViewModel>()  //final processed list
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
                        paymentActivityList.add(
                            PaymentChatResponse.ChatTransactionViewModel(
                                formattedDate,
                                groupedActivities
                            )
                        )  //add to custom model
                        accessedDates.add(formattedDate)               //add date to accessDate Array
                    }


                }
            }


        }
        return paymentActivityList //pass this to adapter
    }


}