package com.app.emcashmerchant.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.emcashmerchant.ui.login.viewModel.LoginViewModel
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_register_otp.*
import timber.log.Timber
import java.lang.Exception


class OtpActivity : AppCompatActivity() {


    private val sessionStorage by lazy {
        SessionStorage(this)
    }
    private val dialog by lazy {
        AppDialog(this)

    }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        observe()


        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e(task.exception, "Fetching FCM registration token failed")
                    viewModel.fcmToken = task.result.toString()
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                viewModel.fcmToken = task.result.toString()
                Timber.d(viewModel.fcmToken.toString())
            })

        } catch (exception: Exception) {
            Timber.d(exception.toString())

        }



        tv_description.text =
            getString(R.string.pleaseEnterTheOtp).plus(sessionStorage.merchantNumber)
    }

    fun onClick(view: View) {
        val otp = otp_layout.obtainOTP()
        when (view.id) {
            R.id.ll_resend_otp -> {
                viewModel.performResendOtp(sessionStorage.referenceId.toString())

            }
            R.id.btn_verify_otp -> {
                if (otp.isNotEmpty() && otp.length == 4) {

                    goToPinNumberPage(
                        otp, sessionStorage.referenceId.toString()
                    )

                } else {
                    showLongToast(getString(R.string.enter_valid_otp))
                }
            }
        }
    }


    private fun observe() {
        viewModel.apply {

            loginOtpStatus.observe(this@OtpActivity, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
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
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

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
        viewModel.performVerifyOtp(otp, referenceid, viewModel.fcmToken.toString())
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
        sessionStorage.isLoggedIn = true
    }

}