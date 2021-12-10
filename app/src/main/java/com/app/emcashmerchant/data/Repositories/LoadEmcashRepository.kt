package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.PayerAuthenticatorRequest
import com.app.emcashmerchant.data.model.request.PaymentByExistingCardRequest
import com.app.emcashmerchant.data.model.request.PaymentByNewCardRequest
import com.app.emcashmerchant.data.model.request.TopUpRequest
import com.app.emcashmerchant.data.model.response.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class LoadEmcashRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun topUp(
        topUpRequest: TopUpRequest,
        onApiCallback: (status: Boolean, message: String?, result: TopUpResponse?) -> Unit
    ) {
        api.topupEmCash(topUpRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = { data ->
                onApiCallback(true, null, data)
            }
        )
    }


    fun bankCardsListing(onApiCallback: (status: Boolean, message: String?, result: BankCardsListingResponse.Data?) -> Unit) {
        api.getBankCard().awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var data = it?.data
                data?.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }

    fun paymentByExistingCard(
        paymentByExistingCardRequest: PaymentByExistingCardRequest,
        onApiCallback: (status: Boolean, message: String?, result: PaymentByExisitingCardResponse?) -> Unit
    ) {
        api.paymentByExistingCard(paymentByExistingCardRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = { data ->
                onApiCallback(true, null, data)
            }
        )
    }

    fun paymentByNewCard(
        paymentByNewCardRequest: PaymentByNewCardRequest,
        onApiCallback: (status: Boolean, message: String?, result: PaymentByNewCardResponse?) -> Unit
    ) {
        api.paymentByNewCard(paymentByNewCardRequest)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)
                }, onSuccess = { data ->
                    onApiCallback(true, null, data)
                }
            )
    }

    fun authenticatePayer(
        payerAuthenticatorRequest: PayerAuthenticatorRequest,
        onApiCallback: (status: Boolean, message: String?, result: PayerAuthenticatorResponse?) -> Unit
    ) {
        api.authenticatePayer(payerAuthenticatorRequest)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)
                }, onSuccess = { data ->
                    onApiCallback(true, null, data)
                }
            )
    }

}