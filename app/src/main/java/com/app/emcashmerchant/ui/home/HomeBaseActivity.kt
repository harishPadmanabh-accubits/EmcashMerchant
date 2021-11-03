package com.app.emcashmerchant.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.DESTINATION
import com.app.emcashmerchant.utils.KEY_USER_ID_FROM_DEEPLINK

class HomeBaseActivity : AppCompatActivity() {


    val destination:Int  by lazy {
        intent.getIntExtra(DESTINATION,0)
    }

    val userIdFromDeeplink by lazy {
        intent.getStringExtra(KEY_USER_ID_FROM_DEEPLINK)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_base)



    }

}