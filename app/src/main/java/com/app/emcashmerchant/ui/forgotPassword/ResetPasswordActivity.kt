package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.extensions.*
import com.app.emcashmerchant.Authviewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var sessionStorage: SessionStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        initViews()
        initViewModel()
        setupObservers()
    }

    fun onClick(view: View) {
        var password = fet_new_password.getInput()
        var confirmPasword:String=fet_confirm_password.getInput()
        when(view.id){
            R.id.iv_back->{
                onBackPressed()
            }
            R.id.btn_confirm->{
                resetPassword(password,confirmPasword)
            }
        }
    }
    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun initViewModel() {
        viewModel = obtainViewModel(ForgotPasswordViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.apply {
            performResetPasswordStatus.observe(this@ResetPasswordActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                    }
                    ApiCallStatus.SUCCESS -> {
                        val data = it.data
                        openActivity(PasswordResetCompleteActivity::class.java)

                    }
                    ApiCallStatus.ERROR -> {
                        showShortToast(it.errorMessage)
                    }
                }

            })
        }

    }

    private fun resetPassword(password:String,confirmPassword:String)
    {
        if(isValidPassword(password,confirmPassword ))
        {
            viewModel.performResetPassword(password,sessionStorage.getReferenceIdSession().toString())
        }


    }

}