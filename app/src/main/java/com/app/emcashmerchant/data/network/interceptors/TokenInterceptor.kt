package com.app.emcashmerchant.data.network.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request

class TokenInterceptor(var appContext: Context) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "Bearer $")
            .build()

        return chain.proceed(newRequest)

    }
}