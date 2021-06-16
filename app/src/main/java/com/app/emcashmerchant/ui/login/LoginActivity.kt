package com.app.emcashmerchant.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.afterTextChanged
import com.app.emcashmerchant.utils.extensions.isEmailValid
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getViewModel()
        observe()
        checkForValidEmail()

    }

    private fun checkForValidEmail() {
        fet_email.formInput.afterTextChanged { email->
            if(email.isEmailValid())
                fet_email.showCheckMark()
            else
                fet_email.hideCheckMark()
        }
    }

    private fun observe() {
        viewModel.apply {
            passwordVisibiltyData.observe(this@LoginActivity, Observer {state->
                when(state){
                    PasswordVisibilty.PSWD_HIDE->{

                    }
                    PasswordVisibilty.PSWD_SHOW->{

                    }

                }

            })
        }
    }

    private fun getViewModel() {
        viewModel = obtainViewModel(LoginViewModel::class.java)
    }

    fun onClick(view: View) {}
}