package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.extensions.*
import com.app.emcashmerchant.ui.forgotPassword.viewModel.ForgotPasswordViewModel
import com.app.emcashmerchant.utils.AppDialog
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    private val viewModel: ForgotPasswordViewModel by viewModels()
    private val sessionStorage by lazy {
        SessionStorage(this)
    }
    private val dialog by lazy { AppDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        setupObservers()

    }

    fun onClick(view: View) {
        val password = fet_new_password.getInput()
        val confirmPasword: String = fet_confirm_password.getInput()
        when (view.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.btn_confirm -> {
                resetPassword(password, confirmPasword)
            }
        }
    }


    private fun setupObservers() {
        viewModel.apply {
            performResetPasswordStatus.observe(this@ResetPasswordActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        openActivity(PasswordResetCompleteActivity::class.java)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }

            })
        }

    }

    private fun resetPassword(password: String, confirmPassword: String) {
        if (isValidPassword(password, confirmPassword)) {
            viewModel.performResetPassword(password, sessionStorage.referenceId.toString())
        }


    }

}