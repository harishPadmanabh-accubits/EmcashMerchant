package com.app.emcashmerchant.ui.convertEmcashTocash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.modelrequest.WithDrawRequest
import com.app.emcashmerchant.data.models.TopUpResponse
import com.app.emcashmerchant.data.models.WithDrawResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.LoadEmcashRepository
import com.app.emcashmerchant.data.network.Repositories.WithDrawEmcashRepository

class ConvertEmcashViewModel(val app: Application): AndroidViewModel(app) {
    var withDrawStatus = MutableLiveData<ApiMapper<WithDrawResponse>>()
    val repository = WithDrawEmcashRepository(app)

    fun withDraw(withDrawRequest: WithDrawRequest) {
        withDrawStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.withDraw(withDrawRequest) { status, message, result ->
            when (status) {
                true -> {
                    withDrawStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    withDrawStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }
}