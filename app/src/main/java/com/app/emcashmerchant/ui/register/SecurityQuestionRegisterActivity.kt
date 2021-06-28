package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.utils.extensions.openActivity

class SecurityQuestionRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_question__register)
    }

    fun onClick(view: View)
    {
        when(view.id){
            R.id.btn_next->
            {
                openActivity(UploadDocumentsActivity::class.java)
            }
            R.id.iv_back ->{
                onBackPressed()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}