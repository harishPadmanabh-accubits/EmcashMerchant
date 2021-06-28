package com.app.emcashmerchant.data.repositories

import android.content.Context
import android.util.Log
import com.app.emcashmerchant.data.AppPreferences
import com.app.emcashmerchant.data.models.SignupInitialRequestBody
import com.app.emcashmerchant.data.models.SignupInitialResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse
import com.skydoves.sandwich.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AuthRepository(val context: Context) {

    private val appPreferences = AppPreferences.init(context)
    private val api = ApiManger(context).api

    fun performInitialSignup(
        signUpData: SignupInitialRequestBody,
        onResult: (status: Boolean, message: String?, result: SignupInitialResponse.Data?) -> Unit
    ) {
        api.performInitialSignup(signUpData).awaitResponse(
            onFailure = {
                onResult(false, it, null)
            }, onSuccess = {
                val data = it?.data
                data?.let {
                    appPreferences.refId = it.referenceId
                    onResult(true, null, data)
                }
            })
    }


}


