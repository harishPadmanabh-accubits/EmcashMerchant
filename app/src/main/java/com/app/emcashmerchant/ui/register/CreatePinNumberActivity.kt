package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.openActivity

class CreatePinNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pin_number)
    }

    fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.btn_next->{
                openActivity(SecurityQuestionRegisterActivity::class.java)

            }
            R.id.iv_back->{
                onBackPressed()

            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}