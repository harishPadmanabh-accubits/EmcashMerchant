package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.Authviewmodel.RegisterViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_register_otp.*

class RegisterOtpActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_otp)
        initViews()
        initViewModel()
        setupObservers()
        dialog= AppDialog(this)
        tv_description.text="Please enter the OTP sent to your registered mobile number ${sessionStorage.merchantNumber}"

    }

    fun onClick(view: View) {
        val otp = otp_layout.obtainOTP()
        when (view.id) {
            R.id.btn_verify_otp -> {
                if (otp.isNotEmpty() && otp.length == 4) {
                    viewModel.performVerifyOtp(otp, sessionStorage.referenceIdInitial.toString()
                    )

                } else {
                    showLongToast(getString(R.string.enter_valid_otp))
                }
            }
            R.id.ll_resend_otp -> {
                viewModel.performResendOtp( sessionStorage.referenceIdInitial.toString())

            }
            R.id.iv_back -> {
                finish()
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            verifyOtpStatus.observe(this@RegisterOtpActivity, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //progressbar

                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        var data = it.data
                        sessionStorage.referenceIdOtp=data?.referenceId
                        openActivity(CreatePasswordActivity::class.java)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)

                    }
                }
            })

            resendOtpStatus.observe(this@RegisterOtpActivity, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //progressbar
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        var data = it.data

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


    private fun initViewModel() {
        viewModel = obtainViewModel(RegisterViewModel::class.java)
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }


}