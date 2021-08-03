package com.app.emcashmerchant.ui.home.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.NotificationResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.NotificationRepository
import com.app.emcashmerchant.utils.extensions.dateFormat
import timber.log.Timber

class NotificationsViewModel(val app: Application) : AndroidViewModel(app) {
    var notificationStatus = MutableLiveData<ApiMapper<NotificationResponse.Data>>()
    val repository = NotificationRepository(app)

    fun notifications(page:Int,limit:Int) {
        notificationStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.notifications(page,limit) { status, message, result ->
            when (status) {
                true -> {
                    notificationStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    notificationStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun groupNotification(rows: ArrayList<NotificationResponse.Data.Row>?): ArrayList<NotificationResponse.NotificationViewModel> {

        val NotificationActivityList = ArrayList<NotificationResponse.NotificationViewModel>()  //final processed list
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
                        NotificationActivityList.add(
                            NotificationResponse.NotificationViewModel(
                                formattedDate,
                                groupedActivities
                            )
                        )  //add to custom model
                        accessedDates.add(formattedDate)               //add date to accessDate Array
                    }


                }
            }


        }
        return NotificationActivityList //pass this to adapter
    }



}