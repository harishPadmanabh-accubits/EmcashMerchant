package com.app.emcashmerchant.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.app.emcashmerchant.R

class HomeBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_base)

    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
    }
}