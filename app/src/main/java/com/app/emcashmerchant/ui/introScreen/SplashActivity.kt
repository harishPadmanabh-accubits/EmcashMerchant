package com.app.emcashmerchant.ui.introScreen

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.ui.ReUploadDocuments.ReUploadDocumentsActivity
import com.app.emcashmerchant.ui.login.PinNumberActivity
import com.app.emcashmerchant.utils.IS_FROM_DEEPLINK
import com.app.emcashmerchant.utils.KEY_DEEPLINK
import com.app.emcashmerchant.utils.KEY_REUPLOAD_TOKEN
import com.app.emcashmerchant.utils.KEY_TYPE
import com.app.emcashmerchant.utils.extensions.openActivity

class SplashActivity : AppCompatActivity() {
    var type: String? = null
    private var deepLink: String? = null

    val isFromDeeplink by lazy {
        intent.getBooleanExtra(IS_FROM_DEEPLINK, false)
    }

    lateinit var sessionStorage: SessionStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sessionStorage = SessionStorage(this)

        type = intent.getStringExtra(KEY_TYPE)
        deepLink = intent.getStringExtra(KEY_DEEPLINK)
        deepLink?.let { Log.d("KEY_DEEPLINK_SPLASH", it) }

        pageDirection()

    }


    private fun pageDirection() {
        Handler().postDelayed({
            if (sessionStorage.isLoggedIn) {
                if (isFromDeeplink) {

                    if (deepLink?.isNotEmpty() == true) {
                        if (Uri.parse(deepLink.toString()).pathSegments[1].equals("ReUpload")) {
                            openActivity(ReUploadDocumentsActivity::class.java) {
                                val token = Uri.parse(deepLink.toString()).pathSegments[2]
                                this.putString(KEY_DEEPLINK, deepLink)
                                this.putString(KEY_REUPLOAD_TOKEN, token)
                            }
                        } else if (Uri.parse(deepLink.toString()).pathSegments[1].equals("paymentHistory")) {
                            openActivity(PinNumberActivity::class.java) {
                                this.putString(KEY_DEEPLINK, deepLink)
                                this.putBoolean(IS_FROM_DEEPLINK, true)
                            }
                        }
                    }else {
                        openActivity(PinNumberActivity::class.java)

                    }


                } else {
                    openActivity(PinNumberActivity::class.java)

                }
            } else {
                if (isFromDeeplink) {
                    if (deepLink?.isNotEmpty() == true) {
                        if (Uri.parse(deepLink.toString()).pathSegments[1].equals("ReUpload")) {
                            openActivity(ReUploadDocumentsActivity::class.java) {
                                val token = Uri.parse(deepLink.toString()).pathSegments[2]

                                this.putString(KEY_DEEPLINK, deepLink)
                                this.putString(KEY_REUPLOAD_TOKEN, token)
                            }
                        }
                    }
                    else {
                        openActivity(IntroActivity::class.java)

                    }

                } else {
                    openActivity(IntroActivity::class.java)

                }


            }


        }, 3000)

    }
}
