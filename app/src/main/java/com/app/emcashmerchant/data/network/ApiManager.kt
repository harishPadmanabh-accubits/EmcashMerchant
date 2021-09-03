package com.app.emcashmerchant.data.network

import android.content.Context
import com.app.emcashmerchant.BuildConfig
import com.app.emcashmerchant.data.authenticator.TokenAuthenticator
import com.app.emcashmerchant.data.network.interceptors.NetworkConnectionInterceptor
import com.app.emcashmerchant.utils.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiManger(appContext: Context) {
    var api: ApiServices

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val gson = GsonBuilder().serializeNulls().create()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(4000, TimeUnit.SECONDS)
            .readTimeout(4000, TimeUnit.SECONDS)
//            .addInterceptor(TokenInterceptor(appContext))
            .authenticator(TokenAuthenticator(appContext))
            .build()


        val restAdapter = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        api = restAdapter.create(ApiServices::class.java)
    }


}
