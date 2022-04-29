package com.app.emcashmerchant.data.network.interceptors

import android.content.Context
import com.app.emcashmerchant.data.SessionStorage
import okhttp3.*
import timber.log.Timber
import java.io.IOException


class TokenInterceptor(var context: Context) : Interceptor {

    private val appContext: Context = context.applicationContext
    private val accestoken =  SessionStorage(appContext).accesToken
    private val reuploadToken =  SessionStorage(appContext).reuploadToken

    @Throws(IOException::class)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val shouldAddReUploadAuthHeaders = originalRequest.headers["isReUpload"] .equals("true")
        Timber.e("hhp reUpload $shouldAddReUploadAuthHeaders $reuploadToken")

        val builder = originalRequest.newBuilder()
        builder.apply {
            addHeader("Accept", "application/json")
            if(shouldAddReUploadAuthHeaders){
                addHeader("Authorization","Bearer ${reuploadToken}")
                Timber.e("reupload added Bearer ${reuploadToken}")
            }else if(!accestoken.isNullOrEmpty() && !shouldAddReUploadAuthHeaders){
                addHeader("Authorization","Bearer ${accestoken}")
                Timber.e("accestoken added Bearer ${accestoken}")
            }
        }
        val request = builder.build()
        return chain.proceed(request)
    }
}