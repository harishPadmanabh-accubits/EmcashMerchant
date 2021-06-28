package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.openActivity

class RegisterOtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_otp)
    }

    fun onClick(view:View)
    {
        when(view.id)
        {
            R.id.btn_verify_otp->{
                openActivity(CreatePasswordActivity::class.java)
            }
            R.id.iv_back-> {
                finish()
            }
        }
    }
}