package com.app.emcashmerchant.data.network

import android.content.Context
import com.app.emcashmerchant.BuildConfig
import com.app.emcashmerchant.data.network.authenticator.TokenAuthenticatorV2
import com.app.emcashmerchant.data.network.interceptors.NetworkConnectionInterceptor
import com.app.emcashmerchant.data.network.interceptors.TokenInterceptor
import com.app.emcashmerchant.utils.BASE_URL
import com.app.emcashmerchant.utils.RELEASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiManger(appContext: Context) {
    var api: EmCashApiServices

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val gson = GsonBuilder().serializeNulls().create()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(4000, TimeUnit.SECONDS)
            .readTimeout(4000, TimeUnit.SECONDS)
            .authenticator(TokenAuthenticatorV2(appContext,buildTokenApi()))
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .addInterceptor(TokenInterceptor(appContext))
            .addInterceptor(loggingInterceptor)
            .build()


        val restAdapter = Retrofit.Builder()
            .baseUrl(RELEASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        api = restAdapter.create(EmCashApiServices::class.java)
       // ApiInstanceHolder.setApis(api)
    }

    private fun getOkHttpClient(authenticator: Authenticator?=null):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                authenticator?.let { client.authenticator(it) }

                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel( HttpLoggingInterceptor.Level.HEADERS)
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }


            }.build()
    }

    private fun buildTokenApi(): TokenRefreshApi {
        return Retrofit.Builder()
            .baseUrl(RELEASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenRefreshApi::class.java)
    }

}
