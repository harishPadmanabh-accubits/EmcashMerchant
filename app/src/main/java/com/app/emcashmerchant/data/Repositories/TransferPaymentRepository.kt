package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.CheckQrCodeRequest
import com.app.emcashmerchant.data.model.request.InitiateContactPaymentRequest
import com.app.emcashmerchant.data.model.request.RejectAcceptRequest
import com.app.emcashmerchant.data.model.request.TransferAmountRequest
import com.app.emcashmerchant.data.model.response.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class TransferPaymentRepository(val context: Context) {
    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun QrCodeCheck(
        qrCodeRequest: CheckQrCodeRequest,
        onApiCallback: (status: Boolean, message: String?, result: CheckQrCodeResponse.Data?) -> Unit
    ) {
        api.checkQR(qrCodeRequest).awaitResponse(
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


    fun performReject(
        request: RejectAcceptRequest,
        onApiCallback: (status: Boolean, message: String?, result: RejectResponse?) -> Unit
    ) {
        api.rejectPayment(request)
            .awaitResponse(
                onFailure = {
                    onApiCallback(false, it, null)
                }, onSuccess = {
                    it?.let {
                        onApiCallback(true, null, it)
                    }
                })
    }

    fun performAccept(
        request: RejectAcceptRequest,
        onApiCallback: (status: Boolean, message: String?, result: AcceptResponse?) -> Unit
    ) {
        api.acceptPayment(request)
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
        api.getAllContacts(1, 100, search)
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
        api.getOneContactResponse(userId)
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
        initiateContactPaymentRequest: InitiateContactPaymentRequest,
        onApiCallback: (status: Boolean, message: String?, result: IntiateContactPaymentResponse.Data?) -> Unit

    ) {
        api.initiatePayment(initiateContactPaymentRequest)
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
        api.getRecentTransactions(1, 10).awaitResponse(
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