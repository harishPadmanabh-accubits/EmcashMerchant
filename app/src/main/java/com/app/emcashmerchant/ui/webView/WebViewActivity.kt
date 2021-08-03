package com.app.emcashmerchant.ui.webView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.KEY_BUISINESS_NAME
import com.app.emcashmerchant.utils.KEY_DEEPLINK
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    private val deepLink by lazy {
        intent.getStringExtra(KEY_DEEPLINK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.settings.setJavaScriptEnabled(true)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(deepLink.toString())
        webView.getSettings().javaScriptEnabled = true;


    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)

    }


}