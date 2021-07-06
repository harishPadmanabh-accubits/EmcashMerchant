package com.app.emcashmerchant.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.Authviewmodel.LoginViewModel
import com.app.emcashmerchant.utils.AppDialog
import kotlinx.android.synthetic.main.activity_register_otp.*

class OtpActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        initViews()
        getViewModel()
        observe()
        dialog= AppDialog(this)

        tv_description.text="Please enter the OTP sent to your registered mobile number ${sessionStorage.merchantNumber}"
    }

    fun onClick(view: View) {
        val otp = otp_layout.obtainOTP()
        when (view.id) {
            R.id.ll_resend_otp -> {
                viewModel.performResendOtp(sessionStorage.getReferenceIdSession().toString())

            }
            R.id.btn_verify_otp -> {
                if (otp.isNotEmpty() && otp.length == 4) {

                    goToPinNumberPage(
                        otp, sessionStorage.getReferenceIdSession().toString()
                    )

                } else {
                    showLongToast(getString(R.string.enter_valid_otp))
                }
            }
        }
    }

    private fun getViewModel() {
        viewModel = obtainViewModel(LoginViewModel::class.java)
    }


    private fun observe() {
        viewModel.apply {
            loginOtpStatus.observe(this@OtpActivity, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        val data = it.data
                        storeDataSession(
                            data?.tokens?.accessToken,
                            data?.tokens?.refreshToken,
                            data?.email,
                            data?.phoneNumber
                        )
                        openActivity(PinNumberActivity::class.java)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()

                        showShortToast(it.errorMessage)
                    }
                }

            })

            resendOtpStatus.observe(this@OtpActivity, androidx.lifecycle.Observer {
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

                    }
                }
            })
        }
    }

    private fun goToPinNumberPage(otp: String, referenceid: String) {
        viewModel.performVerifyOtp(otp, referenceid)
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }

    private fun storeDataSession(
        accessToken: String?,
        refereshToken: String?,
        email: String?,
        phoneNumber: String?
    ) {
        sessionStorage.accesToken = accessToken
        sessionStorage.refreshToken = refereshToken
        sessionStorage.merchantEmail = email
        sessionStorage.merchantNumber = phoneNumber
        sessionStorage.isLoggedIn=true
    }

}