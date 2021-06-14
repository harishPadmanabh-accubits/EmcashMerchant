package com.app.emcashmerchant.data.network.interceptors

import android.content.Context
import com.app.emcashmerchant.data.network.exceptions.NoInternetException
import com.app.emcashmerchant.utils.CommonUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor (private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!CommonUtils.hasInternet(context)){
            throw NoInternetException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}
