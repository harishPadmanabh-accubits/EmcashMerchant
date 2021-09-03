package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.PaymentByExisitingCardRequest
import com.app.emcashmerchant.data.modelrequest.PaymentByNewCardRequest
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class LoadEmcashRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun topUp(topUpRequest: TopUpRequest,onApiCallback: (status: Boolean, message: String?, result: TopUpResponse?) -> Unit){
        api.topUp(topUpRequest,"Bearer ${sessionStorage.accesToken}").awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var  data=it
                data.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }


    fun bankCardsListing(onApiCallback: (status: Boolean, message: String?, result: BankCardsListingResponse.Data?) -> Unit){
        api.getBankCard("Bearer ${sessionStorage.accesToken}").awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var  data=it?.data
                data?.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }
    fun paymentByExistingCard(paymentByExisitingCardRequest: PaymentByExisitingCardRequest,onApiCallback: (status: Boolean, message: String?, result: PaymentByExisitingCardResponse?) -> Unit){
        api.paymentByExistingCard("Bearer ${sessionStorage.accesToken}",paymentByExisitingCardRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var  data=it
                data?.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }

    fun paymentByNewCard(paymentByNewCardRequest: PaymentByNewCardRequest,onApiCallback: (status: Boolean, message: String?, result: PaymentByNewCardResponse?) -> Unit){
        api.paymentByNewCard("Bearer ${sessionStorage.accesToken}",paymentByNewCardRequest).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var  data=it
                data?.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }

}