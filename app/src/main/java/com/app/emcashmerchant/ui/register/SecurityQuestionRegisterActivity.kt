package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.register.adapter.SecurityQuestionAdapter
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.SignupSecurityRequestBody
import com.app.emcashmerchant.data.models.SecurityQuestionsResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.Authviewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_security_question__register.*

class SecurityQuestionRegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sessionStorage: SessionStorage
    private lateinit var securityQuestionAdapter: SecurityQuestionAdapter
    var questionOneId: String? = null
    var questionTwoId: String? = null

    var answerOne: String = ""
    var answerTwo: String = ""
    lateinit var dialog:AppDialog
    private val password by lazy {
        intent.getStringExtra(KEY_PASSWORD)
    }

    private val pinNumber by lazy {
        intent.getStringExtra(KEY_PIN)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_question__register)
        initViews()
        initViewModel()
        setupObservers()
        dialog= AppDialog(this)
        viewModel.getSecurityQuestions()


    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_next -> {
                gotoUploadDocument()
            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(RegisterViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.apply {
            securityQuestionStatus.observe(this@SecurityQuestionRegisterActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        var responseData = it.data
                        loadDataToSpinner(responseData?.data)
                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })

            securitySignupStatus.observe(this@SecurityQuestionRegisterActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        var responseData = it.data
                        sessionStorage.referenceIdSecurity=responseData?.data?.referenceId
                        openActivity(UploadDocumentsActivity::class.java)
                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })

        }
    }

    private fun loadDataToSpinner(questionsList: List<SecurityQuestionsResponse.Data>?) {

        if (questionsList != null) {
            val arrayList = ArrayList(questionsList)
            arrayList.add(
                0, SecurityQuestionsResponse.Data(
                    "0", "0", "Select your Question", false, "0"
                )
            )
            securityQuestionAdapter = SecurityQuestionAdapter(this, arrayList)
            spinner_ques_1.adapter = securityQuestionAdapter
            spinner_ques_2.adapter = securityQuestionAdapter
            spinner_ques_1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    questionOneId = arrayList[position].id
                }

            }
            spinner_ques_2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    questionTwoId = arrayList[position].id
                }

            }

        } else {
            showLongToast(getString(R.string.no_question))
        }


    }

    private fun gotoUploadDocument() {
        answerOne = et_ans_1.text.toString()
        answerTwo = et_ans_2.text.toString()


        if (questionOneId == questionTwoId && !questionOneId.equals("0")  &&  !questionTwoId.equals("0")) {
            showLongToast(getString(R.string.select_different))

        } else if (questionOneId == null|| questionTwoId == null) {
            showLongToast(getString(R.string.please_select_any_question))

        }
        else if( questionOneId.equals("0") ||  questionTwoId.equals("0")){
            showLongToast(getString(R.string.please_select_any_question))
        }
        else if (answerOne.isEmpty()  || answerTwo.isEmpty() || answerOne.length<3 || answerOne.length<3) {
            showLongToast(getString(R.string.please_answer_both_question))

        }
        else {

            SecuritySignUp()


        }
    }

    private fun SecuritySignUp() {
        val signupRequestBody =
            SignupSecurityRequestBody(
                sessionStorage.referenceIdOtp.toString(),
                password.toString(),
                pinNumber.toString(),
                questionOneId.toString(),
                answerOne.toString(),
                questionTwoId.toString(),
                answerTwo.toString()
            )
        viewModel.performSecuritySignup(
            signupRequestBody
        )

    }


}