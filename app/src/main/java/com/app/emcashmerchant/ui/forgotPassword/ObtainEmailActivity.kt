package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.ui.forgotPassword.viewModel.ForgotPasswordViewModel
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.isEmailValidity
import kotlinx.android.synthetic.main.activity_obtain_email.*

class ObtainEmailActivity : AppCompatActivity() {
    private val viewModel: ForgotPasswordViewModel by viewModels()
    private val sessionStorage by lazy {
        SessionStorage(this)
    }

    private val questionOneId by lazy {
        intent.getStringExtra(KEY_QUESTION_ID_1)
    }
    private val questionTwoId by lazy {
        intent.getStringExtra(KEY_QUESTION_ID_2)
    }
    private val answerOne by lazy {
        intent.getStringExtra(KEY_ANSWER_1)
    }
    private val answerTwo by lazy {
        intent.getStringExtra(KEY_ANSWER_2)
    }
    private var email: String = ""
    private val dialog by lazy {
        AppDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obtain_email)

        setupObservers()

    }


    fun onClick(view: View) {
        email = fet_email.getInput()
        when (view.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.btn_next -> {


                goToVerifyOtpActivity(email)


            }
        }
    }

    private fun setupObservers() {
        viewModel.performPasswordRequestStatus.observe(this, Observer {
            when (it.status) {
                ApiCallStatus.LOADING -> {
                    dialog.show_dialog()

                }
                ApiCallStatus.SUCCESS -> {
                    dialog.dismiss_dialog()

                    val data = it.data
                    sessionStorage.referenceId = data?.referenceId
                    openActivity(VerifyOtpActivity::class.java)

                }
                ApiCallStatus.ERROR -> {
                    dialog.dismiss_dialog()
                    showShortToast(it.errorMessage)
                }
            }

        })
    }


    private fun goToVerifyOtpActivity(email: String) {
        if (email.isEmpty()) {
            showShortToast(getString(R.string.enter_valid_email))
        } else if (!email.isEmailValidity()) {
            showShortToast(getString(R.string.enter_valid_email))
        } else if (email.isNotEmpty() && email.isEmailValidity()) {
            viewModel.performPasswordRequest(
                email,
                questionOneId.toString(),
                questionTwoId.toString(),
                answerOne.toString(),
                answerTwo.toString()
            )

        }
    }


}

