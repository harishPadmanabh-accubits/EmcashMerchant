package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.login.OtpActivity
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.isValidPassword
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import kotlinx.android.synthetic.main.activity_basic_details.*
import kotlinx.android.synthetic.main.activity_create_password.*

class CreatePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)
    }
    fun onClick(view: View)
    {
        when(view.id){
            R.id.btn_confirm->{
                fetchPassword()
            }
            R.id.iv_back->{
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun fetchPassword()
    {
        val password = fet_password.getInput()
        val confirmPasword:String=fet_confirm_password.getInput()

        if(isValidPassword(password,confirmPasword))
        {
            openActivity(CreatePinNumberActivity::class.java){
                putString(KEY_PASSWORD,password)
                putString(KEY_CONFIRM_PASSWORD,confirmPasword)
            }
        }

    }
}