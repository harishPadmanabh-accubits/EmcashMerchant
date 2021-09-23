package com.app.emcashmerchant.ui.convertEmcashTocash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.modelrequest.WithDrawRequest
import com.app.emcashmerchant.data.models.BankDetailsResponse
import com.app.emcashmerchant.data.models.WithDrawResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.WithDrawEmcashRepository
import timber.log.Timber

class ConvertEmcashViewModel(val app: Application): AndroidViewModel(app) {
    var withDrawStatus = MutableLiveData<ApiMapper<WithDrawResponse>>()
    val repository = WithDrawEmcashRepository(app)
    var bankDetailsStatus = MutableLiveData<ApiMapper<BankDetailsResponse>>()


    var ibn: String?=""

    fun bankDetails(
    ) {
        bankDetailsStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getBankDetails() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    bankDetailsStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    bankDetailsStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


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