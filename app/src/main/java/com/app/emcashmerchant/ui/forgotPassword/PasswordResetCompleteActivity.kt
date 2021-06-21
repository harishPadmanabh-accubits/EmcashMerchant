package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.login.LoginActivity
import com.app.emcashmerchant.utils.extensions.openActivity

class PasswordResetCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset_complete)
    }

    fun onClick(view: View) {
        openActivity(LoginActivity::class.java)
    }
}