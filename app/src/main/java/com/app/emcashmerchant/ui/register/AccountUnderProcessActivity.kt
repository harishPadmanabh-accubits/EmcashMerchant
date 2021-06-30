package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.login.LoginActivity
import com.app.emcashmerchant.utils.extensions.openActivity

class AccountUnderProcessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_under_process)
    }
    fun onClick(view:View)
    {
        when(view.id) {
            R.id.btn_goto_login->{
                openActivity(LoginActivity::class.java)
            }
        }
    }

    override fun onBackPressed() {
        openActivity(LoginActivity::class.java)

    }
}
