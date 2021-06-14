package com.app.emcashmerchant.ui.splash

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.obtainViewModel


class SplashAcitivity : AppCompatActivity() {
    private lateinit var viewmodel: SplashViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        setContentView(R.layout.activity_splash_acitivity)
        getViewModel()
    }

    private fun getViewModel() {
        viewmodel = obtainViewModel(SplashViewmodel::class.java)
    }
}