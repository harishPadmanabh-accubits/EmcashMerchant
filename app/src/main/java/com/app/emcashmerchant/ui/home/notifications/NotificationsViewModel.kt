package com.app.emcashmerchant.ui.home.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.NotificationResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.NotificationRepository
import com.app.emcashmerchant.ui.home.notifications.PagingSource.NotificationPagingSource
import com.app.emcashmerchant.ui.wallet.PagingSource.WalletTransactionPagingSource
import com.app.emcashmerchant.utils.extensions.dateFormat
import timber.log.Timber

class NotificationsViewModel(val app: Application) : AndroidViewModel(app) {
    var notificationStatus = MutableLiveData<ApiMapper<NotificationResponse.Data>>()
    val repository = NotificationRepository(app)

    private val api = ApiManger(app).api
    private val sessionStorage = SessionStorage(app)


    val notification = Pager(PagingConfig(10)){
        NotificationPagingSource(api,sessionStorage.accesToken.toString())
    }.flow.cachedIn(viewModelScope)





}