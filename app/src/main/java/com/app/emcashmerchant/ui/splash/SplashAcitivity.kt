package com.app.emcashmerchant.ui.splash

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.login.LoginActivity
import com.app.emcashmerchant.ui.register.RegisterActivity
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity


class SplashAcitivity : AppCompatActivity() {
    private lateinit var viewmodel: SplashViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitivity)
        getViewModel()
    }

    private fun getViewModel() {
        viewmodel = obtainViewModel(SplashViewmodel::class.java)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.btn_login -> openActivity(LoginActivity::class.java)
            R.id.btn_register -> openActivity(RegisterActivity::class.java)
        }

    }
}