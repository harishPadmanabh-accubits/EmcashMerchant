package com.app.emcashmerchant.ui.forgotPassword

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.forgotPassword.viewModel.ForgotPasswordViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_verify_otp.*


class VerifyOtpActivity : AppCompatActivity() {

    private val viewModel: ForgotPasswordViewModel by viewModels()
    private val sessionStorage by lazy { SessionStorage(this) }
    private val dialog by lazy { AppDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        setupObservers()

    }

    fun onClick(view: View) {
        val otp = otp_layout.obtainOTP()

        when (view.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.btn_verify_otp -> {

                verifyOtp(otp)
            }
            R.id.ll_resend_otp -> {
                viewModel.performForgotPasswordOtpResend(sessionStorage.referenceId.toString())
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            performForgotPasswordOtpStatus.observe(this@VerifyOtpActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        sessionStorage.referenceId = it.data?.referenceId
                        openActivity(ResetPasswordActivity::class.java)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }

            })

            performForgotPasswordOtpResendStatus.observe(this@VerifyOtpActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        showLongToast(getString(R.string.otp_resend_msg))

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }

            })
        }

    }

    private fun verifyOtp(otp: String) {
        if (otp.isNotEmpty() && otp.length == 4) {
            viewModel.performForgotPasswordOtpVerify(
                otp,
                sessionStorage.referenceId.toString()
            )

        } else {
            showLongToast(getString(R.string.enter_valid_otp))
        }
    }
}