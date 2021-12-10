package com.app.emcashmerchant.ui.home.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.Repositories.NotificationRepository
import com.app.emcashmerchant.data.model.response.BankCardsListingResponse
import com.app.emcashmerchant.data.model.response.NotificationClickResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.ui.home.notifications.PagingSource.NotificationPagingSource

class NotificationsViewModel(val app: Application) : AndroidViewModel(app) {
    val repository = NotificationRepository(app)
    var markNotificationAsRead = MutableLiveData<ApiMapper<NotificationClickResponse.Data>>()

    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)


    val notification = Pager(PagingConfig(10)) {
        NotificationPagingSource(api, sessionStorage.accesToken.toString())
    }.flow.cachedIn(viewModelScope)


    fun onNotificationClick(id: String) {
        markNotificationAsRead.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.onNotificationItemClick(id) { status, message, result ->
            when (status) {
                true -> {
                    markNotificationAsRead.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    markNotificationAsRead.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


}