package com.app.emcashmerchant.ui.forgotPassword

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.response.SecurityQuestionsResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.register.adapter.SecurityQuestionAdapter
import com.app.emcashmerchant.ui.register.viewModel.RegisterViewModel
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_security_questions.*

class SecurityQuestionsActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var securityQuestionAdapter: SecurityQuestionAdapter

    var questionOneId: String? = null
    var questionTwoId: String? = null

    var answerOne: String = ""
    var answerTwo: String = ""

    private val dialog by lazy { AppDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_questions)

        setupObservers()
        viewModel.getSecurityQuestions()
    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.btn_next -> {
                goToEmailActivity()
            }
        }
    }


    private fun setupObservers() {
        viewModel.apply {
            securityQuestionStatus.observe(this@SecurityQuestionsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
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


        }
    }

    private fun loadDataToSpinner(questionsList: List<SecurityQuestionsResponse.Data>?) {

        if (questionsList != null) {
            val arrayList = ArrayList(questionsList)
            arrayList.add(
                0, SecurityQuestionsResponse.Data(
                    "0", "0", getString(R.string.select_your_question), false, "0"
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

    private fun goToEmailActivity() {
        answerOne = et_ans_1.text.toString()
        answerTwo = et_ans_2.text.toString()

        if (questionOneId == questionTwoId && !questionOneId.equals("0") && !questionTwoId.equals("0")) {
            showLongToast(getString(R.string.select_different))

        } else if (questionOneId == null || questionTwoId == null) {
            showLongToast(getString(R.string.please_select_any_question))

        } else if (questionOneId.equals("0") || questionTwoId.equals("0")) {
            showLongToast(getString(R.string.please_select_any_question))
        } else if (answerOne.isEmpty() || answerTwo.isEmpty() || answerOne.length < 3 || answerOne.length < 3) {
            showLongToast(getString(R.string.please_answer_both_question))

        } else {

            openActivity(ObtainEmailActivity::class.java) {
                putString(KEY_QUESTION_ID_1, questionOneId)
                putString(KEY_QUESTION_ID_2, questionTwoId)
                putString(KEY_ANSWER_1, answerOne)
                putString(KEY_ANSWER_2, answerTwo)
            }

        }


    }

}