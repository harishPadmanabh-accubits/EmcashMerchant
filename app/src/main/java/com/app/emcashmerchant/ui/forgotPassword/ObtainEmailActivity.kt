package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.KEY_ANSWER_1
import com.app.emcashmerchant.utils.KEY_ANSWER_2
import com.app.emcashmerchant.utils.KEY_QUESTION_ID_1
import com.app.emcashmerchant.utils.KEY_QUESTION_ID_2
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.Authviewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.activity_obtain_email.*

class ObtainEmailActivity : AppCompatActivity() {
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var sessionStorage: SessionStorage
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obtain_email)
        initViews()
        initViewModel()
        setupObservers()
    }


    fun onClick(view: View) {
        email=fet_email.getInput()
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
                    //show loading
                }
                ApiCallStatus.SUCCESS -> {
                    val data = it.data
                    sessionStorage.setReferenceIdSession(data?.referenceId)
                    openActivity(VerifyOtpActivity::class.java)

                }
                ApiCallStatus.ERROR -> {
                    showShortToast(it.errorMessage)
                }
            }

        })
    }


    private fun goToVerifyOtpActivity(email:String) {
        if (email.isNotEmpty()) {
            viewModel.performPasswordRequest(email,questionOneId.toString(),questionTwoId.toString(),answerOne.toString(),answerTwo.toString())

        } else {
            showShortToast(getString(R.string.enter_valid_email))
        }
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(ForgotPasswordViewModel::class.java)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }
}

