package com.app.emcashmerchant.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.emcashmerchant.R

class HomeBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_base)
    }
}