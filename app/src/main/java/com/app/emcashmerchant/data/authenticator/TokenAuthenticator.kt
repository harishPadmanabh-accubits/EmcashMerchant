package com.app.emcashmerchant.data.authenticator

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.app.emcashmerchant.BuildConfig
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.RefreshTokenRequest
import com.app.emcashmerchant.data.models.NotificationResponse
import com.app.emcashmerchant.data.models.RefreshTokenResponse
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.data.network.ApiServices
import com.app.emcashmerchant.data.network.interceptors.NetworkConnectionInterceptor
import com.app.emcashmerchant.utils.BASE_URL
import com.app.emcashmerchant.utils.extensions.awaitResponse
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TokenAuthenticator(val context: Context) : Authenticator {
    private val appContext: Context = context.applicationContext
    private val sessionStorage = SessionStorage(appContext)

    fun client(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .addInterceptor(loggingInterceptor)
            .build()


        return okHttpClient


    }

    val restAdapter = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client())
        .build()
    var api = restAdapter.create(ApiServices::class.java)


    override fun authenticate(route: Route?, responseAuth: Response): Request? {
        Log.d("responseCodeAuth", responseAuth.code.toString())

       getUpdatedToken(responseAuth)


        return null
    }

    fun getUpdatedToken(responseAuth:Response) : Request{

        var refreshTokenRequest = RefreshTokenRequest(sessionStorage.refreshToken.toString())
        api.refreshToken("Bearer ${sessionStorage.accesToken}", refreshTokenRequest)
            .enqueue(object : Callback<RefreshTokenResponse> {
                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<RefreshTokenResponse>,
                    response: retrofit2.Response<RefreshTokenResponse>
                ) {
                    if (response.code() == 200) {
                        sessionStorage.accesToken =
                            response.body()?.data?.tokens?.accessToken.toString()
                        sessionStorage.refreshToken =
                            response.body()?.data?.tokens?.refreshToken.toString()


                    } else if (response.code() == 401) {
                        sessionStorage.logoutUser()
                    }


                }
            })

        return responseAuth.request.newBuilder()
            .header("Authorization", "Bearer ${sessionStorage.accesToken}")
            .build()

    }


}