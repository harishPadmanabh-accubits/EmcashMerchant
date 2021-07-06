package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.adapter.SecurityQuestionAdapter
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.SecurityQuestionsResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.Authviewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_security_questions.*

class SecurityQuestionsActivity : AppCompatActivity() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sessionStorage: SessionStorage
    private lateinit var securityQuestionAdapter: SecurityQuestionAdapter
    var questionOneId: String? = null
    var questionTwoId: String? = null

    var answerOne: String=""
    var answerTwo: String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_questions)
        initViews()
        initViewModel()
        setupObservers()
        viewModel.getSecurityQuestions()
    }



    fun onClick(view: View) {
        when(view.id){
            R.id.iv_back->{
                onBackPressed()
            }
            R.id.btn_next->{
                goToEmailActivity()
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
            securityQuestionStatus.observe(this@SecurityQuestionsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                    }
                    ApiCallStatus.SUCCESS -> {
                        var responseData = it.data
                        loadDataToSpinner(responseData?.data)
                    }
                    ApiCallStatus.ERROR -> {
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

    private fun goToEmailActivity() {
        answerOne = et_ans_1.text.toString()
        answerTwo = et_ans_2.text.toString()

        if (questionOneId == questionTwoId  ) {
            showLongToast(getString(R.string.select_different))
        } else if (questionOneId == null|| questionTwoId == null|| questionOneId.equals("0") ||  questionTwoId.equals("0")) {
            showLongToast(getString(R.string.please_answer_both_question))

        } else if (answerOne.isEmpty()  || answerTwo.isEmpty() || answerOne.length<3 || answerOne.length<3) {
            showLongToast(getString(R.string.please_answer_both_question))

        } else {

            openActivity(ObtainEmailActivity::class.java){
                putString(KEY_QUESTION_ID_1,questionOneId)
                putString(KEY_QUESTION_ID_2,questionTwoId)
                putString(KEY_ANSWER_1,answerOne)
                putString(KEY_ANSWER_2,answerTwo)
            }

        }


    }

}