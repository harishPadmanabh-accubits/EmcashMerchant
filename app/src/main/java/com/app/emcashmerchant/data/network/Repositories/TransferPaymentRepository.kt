package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.CheckQrCodeRequest
import com.app.emcashmerchant.data.modelrequest.IntiateContactPaymentRequest
import com.app.emcashmerchant.data.modelrequest.TransferAmountRequest
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class TransferPaymentRepository(val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun QrCodeCheck(
        qrCodeRequest: CheckQrCodeRequest,
        onApiCallback: (status: Boolean, message: String?, result: CheckQrCodeResponse.Data?) -> Unit
    ) {
        api.qrCodeCheck(qrCodeRequest, "Bearer ${sessionStorage.accesToken}").awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var data = it?.data
                data.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }

    fun performTransferAmount(
        transferAmountRequest: TransferAmountRequest,
        onApiCallback: (status: Boolean, message: String?, result: TransferAmountResponse?) -> Unit
    ) {
        api.transferAmount(transferAmountRequest, "Bearer ${sessionStorage.accesToken}")
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)
                }, onSuccess = {

                    it?.let {
                        onApiCallback(true, null, it)
                    }
                })
    }

    fun getAllContacts(
        search: String,
        onApiCallback: (status: Boolean, message: String?, result: AllContactResponse.Data?) -> Unit

    ) {
        api.allContactsResponse("Bearer ${sessionStorage.accesToken}", 1, 100, search)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)

                }, onSuccess = {
                    it?.data.let {
                        onApiCallback(true, null, it)
                    }
                }
            )
    }

    fun getOneContact(
        userId: String,
        onApiCallback: (status: Boolean, message: String?, result: CustomerContactResponse.Data?) -> Unit

    ) {
        api.getOneContactResponse("Bearer ${sessionStorage.accesToken}", userId)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)

                }, onSuccess = {
                    it?.data.let {
                        onApiCallback(true, null, it)
                    }
                }
            )
    }


    fun intiatePayment(
        intiateContactPaymentRequest: IntiateContactPaymentRequest,
        onApiCallback: (status: Boolean, message: String?, result: IntiateContactPaymentResponse.Data?) -> Unit

    ) {
        api.intiatePayment(intiateContactPaymentRequest, "Bearer ${sessionStorage.accesToken}")
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)

                }, onSuccess = {
                    it?.data.let {
                        onApiCallback(true, null, it)
                    }
                }
            )
    }


    fun getRecentTransactions(onApiCallback: (status: Boolean, message: String?, result: RecentTransactionResponse.Data?) -> Unit) {
        api.recentTransaction("Bearer ${sessionStorage.accesToken}", 1, 10).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                var data = it?.data
                data.let {
                    onApiCallback(true, null, data)

                }
            }
        )
    }
}