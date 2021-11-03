package com.app.emcashmerchant.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.emcashmerchant.ui.login.viewModel.LoginViewModel
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.forgotPassword.SecurityQuestionsActivity
import com.app.emcashmerchant.ui.introScreen.IntroActivity
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private val sessionStorage by lazy {
        SessionStorage(this)
    }
    private val dialog by lazy {
        AppDialog(this)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        observe()
    }

    private fun observe() {

        viewModel.apply {
            initialLoginStatus.observe(this@LoginActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val data = it.data
                        sessionStorage.referenceId = data?.referenceId
                        sessionStorage.merchantNumber = data?.phoneNumber
                        openActivity(OtpActivity::class.java)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)


                    }
                }

            })
        }
    }


    fun onClick(view: View) {

        val email: String = fet_email.getInput()
        val password: String = fet_password.getInput()
        when (view.id) {
            R.id.btn_login -> {
                if (email.isEmpty() && password.isEmpty()) {
                    showLongToast(getString(R.string.please_fill_all_fields))
                } else if (!email.isEmailValidity()) {
                    showLongToast(getString(R.string.enter_valid_email))
                } else if (isValidSinglePassword(password)) {
                    goToOtpActivity(email, password)

                }

            }
            R.id.btn_forgot_pswd -> {
                openActivity(SecurityQuestionsActivity::class.java)
            }
        }
    }

    override fun onBackPressed() {
        openActivity(IntroActivity::class.java)
    }

    private fun goToOtpActivity(email: String?, password: String?) {
        viewModel.performInitialLogin(email.toString(), password.toString())
    }


}
