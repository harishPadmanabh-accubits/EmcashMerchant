package com.app.emcashmerchant.ui.loadEmcash.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.emcashmerchant.data.modelrequest.PayerAuthenticatorRequest
import com.app.emcashmerchant.data.modelrequest.PaymentByExisitingCardRequest
import com.app.emcashmerchant.data.modelrequest.PaymentByNewCardRequest
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.HomeRepository
import com.app.emcashmerchant.data.network.Repositories.LoadEmcashRepository
import timber.log.Timber

class LoadEmcashViewModel(val app: Application): AndroidViewModel(app)  {
    var topupStatus = MutableLiveData<ApiMapper<TopUpResponse>>()
    var bankCardStatus = MutableLiveData<ApiMapper<BankCardsListingResponse.Data>>()
    var paymentByExistingCardStatus = MutableLiveData<ApiMapper<PaymentByExisitingCardResponse>>()
    var paymentByNewCardStatus = MutableLiveData<ApiMapper<PaymentByNewCardResponse>>()
    var payerAuthenticatorStatus = MutableLiveData<ApiMapper<PayerAuthenticatorResponse>>()

    val repository = LoadEmcashRepository(app)


    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var amount:String?=""
    var description:String?=""

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
    fun bankCardListing() {
        bankCardStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.bankCardsListing() { status, message, result ->
            when (status) {
                true -> {
                    bankCardStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    bankCardStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }
    fun paymentByExistingCard(paymentByExisitingCardRequest: PaymentByExisitingCardRequest) {
        paymentByExistingCardStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.paymentByExistingCard(paymentByExisitingCardRequest) { status, message, result ->
            when (status) {
                true -> {
                    paymentByExistingCardStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    paymentByExistingCardStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }
    fun paymentByNewCard(paymentByNewCardRequest: PaymentByNewCardRequest) {
        paymentByNewCardStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.paymentByNewCard(paymentByNewCardRequest) { status, message, result ->
            when (status) {
                true -> {
                    paymentByNewCardStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    paymentByNewCardStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


    fun payerAuthenticator(paymentAuthenticatorRequest: PayerAuthenticatorRequest) {
        payerAuthenticatorStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.payerAuthenticator(paymentAuthenticatorRequest) { status, message, result ->
            when (status) {
                true -> {
                    payerAuthenticatorStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    payerAuthenticatorStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

}