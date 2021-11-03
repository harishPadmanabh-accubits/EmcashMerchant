package com.app.emcashmerchant

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.ui.reUploadDocuments.ReUploadDocumentsActivity
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.DeepLinkRoutes.ROUTE_PAYMENT_HISTORY
import com.app.emcashmerchant.utils.DeepLinkRoutes.ROUTE_REUPLOAD
import com.app.emcashmerchant.utils.extensions.openActivity
import timber.log.Timber

object DeepLinkFactory {
    fun processDeeplink(deeplink:String?,context: Context){
        val sessionStorage = SessionStorage(context)
        deeplink?.let { url->
            val uri = Uri.parse(url)
            when(uri.pathSegments[1]){
                ROUTE_PAYMENT_HISTORY ->{
                    sessionStorage.pendingDeeplink = "https://".plus(url)
                    Timber.e("deepLinkFactory ${ sessionStorage.pendingDeeplink }")
                    context.openActivity(HomeBaseActivity::class.java)
                }
                ROUTE_REUPLOAD->{
                    val token = uri.pathSegments[2]
                    context.startActivity(Intent(context, ReUploadDocumentsActivity::class.java).also {
                        it.putExtra(KEY_REUPLOAD_TOKEN, token)
                    })
                }
            }
        }
    }

}