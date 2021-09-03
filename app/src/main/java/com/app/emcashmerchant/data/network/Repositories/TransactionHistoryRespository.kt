package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.data.models.LogOutResponse
import com.app.emcashmerchant.data.models.TransactionHistoryResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse

class TransactionHistoryRespository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api




}