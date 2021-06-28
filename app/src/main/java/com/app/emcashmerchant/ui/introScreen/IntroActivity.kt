package com.app.emcashmerchant.ui.introScreen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.login.LoginActivity
import com.app.emcashmerchant.ui.register.BasicDetailsActivity
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity


class IntroActivity : AppCompatActivity() {
    private lateinit var viewmodel: IntroScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitivity)
        getViewModel()
    }

    private fun getViewModel() {
        viewmodel = obtainViewModel(IntroScreenViewModel::class.java)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.btn_login -> openActivity(LoginActivity::class.java)
            R.id.btn_register -> openActivity(BasicDetailsActivity::class.java)
        }

    }
}