package com.app.emcashmerchant.data.Repositories

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiManger

class TransactionHistoryRespository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api




}