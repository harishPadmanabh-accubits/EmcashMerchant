package com.app.emcashmerchant.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.showLongToast

class PinNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_number)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.btn_confirm_pin->{
                showLongToast("Open Home page")
            }
            R.id.iv_back->{
                onBackPressed()
            }
        }
    }
}