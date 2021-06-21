package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.openActivity

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.iv_back->{
                onBackPressed()
            }
            R.id.btn_confirm->{
                openActivity(PasswordResetCompleteActivity::class.java)
            }
        }
    }
}