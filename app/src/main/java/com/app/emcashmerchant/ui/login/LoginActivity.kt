package com.app.emcashmerchant.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.emcashmerchant.Authviewmodel.LoginViewModel
import com.app.emcashmerchant.Authviewmodel.PasswordVisibilty
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.forgotPassword.SecurityQuestionsActivity
import com.app.emcashmerchant.ui.introScreen.IntroActivity
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog:AppDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        getViewModel()
        observe()
        dialog= AppDialog(this)
    }

    private fun observe() {
        viewModel.apply {
            passwordVisibiltyData.observe(this@LoginActivity, Observer { state ->
                when (state) {
                    PasswordVisibilty.PSWD_HIDE -> {

                    }
                    PasswordVisibilty.PSWD_SHOW -> {

                    }

                }

            })
        }
        viewModel.apply {
            initialLoginStatus.observe(this@LoginActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val data = it.data
                        sessionStorage.referenceId=data?.referenceId
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

    private fun getViewModel() {
        viewModel = obtainViewModel(LoginViewModel::class.java)
    }

    fun onClick(view: View) {

        var email: String = fet_email.getInput()
        var password: String = fet_password.getInput()
        when (view.id) {
            R.id.btn_login -> {
                if (email.isEmpty() && password.isEmpty()) {
                    showLongToast(getString(R.string.please_fill_all_fields))
                } else if (!email.isEmailValidity()) {
                    showLongToast(getString(R.string.enter_valid_email))
                } else if (isValidSinglePassword(password)) {
                    gotOtpActivity(email, password)

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

    fun gotOtpActivity(email: String?, password: String?) {
        viewModel.performInitialLogin(email.toString(), password.toString())
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }


}
