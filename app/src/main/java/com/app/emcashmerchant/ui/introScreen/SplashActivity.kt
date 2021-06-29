package com.app.emcashmerchant.ui.introScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.utils.extensions.openActivity

class SplashActivity : AppCompatActivity() {
    lateinit var sessionStorage: SessionStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sessionStorage = SessionStorage(this)
        pageDirection()

    }

    private  fun pageDirection() {
        Handler().postDelayed({
            if(sessionStorage.isLoggedIn)
            {

                openActivity(HomeBaseActivity::class.java)
            }
            else
            {
                openActivity(IntroActivity::class.java)

            }


        }, 3000)

    }
}