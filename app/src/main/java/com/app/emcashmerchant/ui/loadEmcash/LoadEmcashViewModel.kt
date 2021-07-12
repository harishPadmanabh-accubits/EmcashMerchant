package com.app.emcashmerchant.ui.loadEmcash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.models.TopUpResponse
import com.app.emcashmerchant.data.models.WalletResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.HomeRepository
import com.app.emcashmerchant.data.network.Repositories.LoadEmcashRepository
import timber.log.Timber

class LoadEmcashViewModel(val app: Application): AndroidViewModel(app)  {
    var topupStatus = MutableLiveData<ApiMapper<TopUpResponse>>()
    val repository = LoadEmcashRepository(app)

    fun topUp(topUpRequest: TopUpRequest) {
        topupStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.topUp(topUpRequest) { status, message, result ->
            when (status) {
                true -> {
                    topupStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    topupStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

}