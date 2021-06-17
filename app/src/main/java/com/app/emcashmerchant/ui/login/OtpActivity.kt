package com.app.emcashmerchant.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.openActivity

class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.ll_resend_otp->{

            }
            R.id.btn_verify_otp ->{
                openActivity(PinNumberActivity::class.java)
            }
        }
    }
}