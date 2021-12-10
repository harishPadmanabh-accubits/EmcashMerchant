package com.app.emcashmerchant.data.network.interceptors

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import okhttp3.*
import timber.log.Timber
import java.io.IOException


class TokenInterceptor(var context: Context) : Interceptor {

    private val appContext: Context = context.applicationContext
    private val accestoken =  SessionStorage(appContext).accesToken
    @Throws(IOException::class)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        builder.apply {
            addHeader("Accept", "application/json")
            if(!accestoken.isNullOrEmpty()){
                addHeader("Authorization","Bearer ${accestoken}")
                Timber.e("accestoken added Bearer ${accestoken}")
            }
        }
        val request = builder.build()
        return chain.proceed(request)
    }
}