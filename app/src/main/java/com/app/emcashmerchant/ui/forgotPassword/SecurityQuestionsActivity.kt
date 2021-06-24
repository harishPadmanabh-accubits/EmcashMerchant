package com.app.emcashmerchant.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.openActivity
import kotlinx.android.synthetic.main.activity_security_questions.*

class SecurityQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_questions)
    }



    fun onClick(view: View) {
        when(view.id){
            R.id.iv_back->{
                onBackPressed()
            }
            R.id.btn_next->{
                openActivity(ObtainEmailActivity::class.java)
            }
        }
    }
}