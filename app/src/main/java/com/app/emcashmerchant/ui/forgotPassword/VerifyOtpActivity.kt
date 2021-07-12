package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.Authviewmodel.ForgotPasswordViewModel
import com.app.emcashmerchant.utils.AppDialog
import kotlinx.android.synthetic.main.activity_verify_otp.otp_layout


class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)
        initViews()
        initViewModel()
        setupObservers()
        dialog= AppDialog(this)

    }

    fun onClick(view: View) {
        var otp = otp_layout.obtainOTP()

        when (view.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.btn_verify_otp -> {

                verifyOtp(otp)
            }
            R.id.ll_resend_otp -> {
                viewModel.performForgotPasswordOtpResend( sessionStorage.referenceId.toString())
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
            performForgotPasswordOtpStatus.observe(this@VerifyOtpActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val data = it.data
                        sessionStorage.referenceId=data?.referenceId
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
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val data = it.data
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