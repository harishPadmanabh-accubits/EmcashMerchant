package com.app.emcashmerchant.data.network.interceptors

import android.content.Context
import android.se.omapi.Session
import android.widget.Toast
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.RefreshTokenRequest
import com.app.emcashmerchant.data.models.NotificationResponse
import com.app.emcashmerchant.data.models.RefreshTokenResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import java.io.IOException


class TokenInterceptor(var context: Context) : Interceptor {

    private val appContext: Context = context.applicationContext
    private val accestoken =  SessionStorage(appContext).accesToken
    @Throws(IOException::class)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorisedRequestBuilder = originalRequest.newBuilder()
            .addHeader("Authorization","Bearer  ${accestoken}")
            .header("Accept", "application/json")
        return chain.proceed(authorisedRequestBuilder.build())
    }
}