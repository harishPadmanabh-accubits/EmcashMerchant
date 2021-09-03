package com.app.emcashmerchant

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.ui.ReUploadDocuments.ReUploadDocumentsActivity
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.ui.login.LoginActivity
import com.app.emcashmerchant.ui.login.PinNumberActivity
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showShortToast
import timber.log.Timber
import java.lang.Exception

object DeepLinkFactory {
    fun processDeeplink(deeplink:String?,context: Context){
        val sessionStorage = SessionStorage(context)
        deeplink?.let { url->
            val uri = Uri.parse(url)
            when(uri.pathSegments[1]){
                "paymentHistory"->{
                    sessionStorage.pendingDeeplink = "https://".plus(url)
                    context.openActivity(HomeBaseActivity::class.java)
                }
                "ReUpload"->{
                    val token = uri.pathSegments[2]
                    context.startActivity(Intent(context, ReUploadDocumentsActivity::class.java).also {
                        it.putExtra(KEY_REUPLOAD_TOKEN, token)
                    })
                }
            }
        }
    }

}