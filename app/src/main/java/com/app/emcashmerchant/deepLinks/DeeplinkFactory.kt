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
import com.app.emcashmerchant.utils.extensions.showShortToast
import timber.log.Timber
import java.lang.Exception

object DeepLinkFactory {


    fun getIntentFromDeeplink(url: Uri, context: Context) {

        Timber.e("deepLink_Factory ${url.pathSegments[1]}")
        val userId = url.pathSegments[2]
        if (url.pathSegments[1].equals("paymentHistory")) {
            Intent(context, HomeBaseActivity::class.java).also {
                it.putExtra(DESTINATION, SCREEN_CHAT)
                it.putExtra(KEY_USER_ID_FROM_DEEPLINK, userId)
            }
        }else if(url.pathSegments[1].equals("ReUpload")){
            Intent(context, ReUploadDocumentsActivity::class.java).also {
                it.putExtra(DESTINATION, SCREEN_CHAT)
                it.putExtra(KEY_REUPLOAD_TOKEN, userId)
            }
        }else{
            context.showShortToast("Invalid Deep Link")
        }
//        return when (url.pathSegments[1]) {
//            "paymentHistory" -> {
//                val userId = url.pathSegments[2]
//                Timber.e("User id from depplink path $userId")
//                Intent(context, HomeBaseActivity::class.java).also {
//                    it.putExtra(DESTINATION, SCREEN_CHAT)
//                    it.putExtra(KEY_USER_ID_FROM_DEEPLINK, userId)
//                }
//
//
//            }
//            else -> throw IllegalArgumentException("Invalid Deeplink ")
//        }
//
//    }
        //
    }

}