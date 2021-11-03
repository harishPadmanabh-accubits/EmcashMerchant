package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import kotlinx.android.synthetic.main.activity_create_pin_number.*

class CreatePinNumberActivity : AppCompatActivity() {

    private val password by lazy {
        intent.getStringExtra(KEY_PASSWORD)
    }
    private val confirmPassword by lazy {
        intent.getStringExtra(KEY_CONFIRM_PASSWORD)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pin_number)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_next -> {
                fetchPinNumber()

            }
            R.id.iv_back -> {
                onBackPressed()

            }
        }
    }

    private fun fetchPinNumber() {
        val pinNumber = fet_pin_number.getInput()
        val confirmPinNumber: String = fet_confirm_pin_number.getInput()

        if (pinNumber != confirmPinNumber) {
            showLongToast(getString(R.string.same_pin_number_validation))
        } else if (pinNumber.isEmpty() || confirmPinNumber.isEmpty()) {
            showLongToast(getString(R.string.please_fill_all_fields))
        }
        else if(pinNumber.length<6 || confirmPinNumber.length<6){
            showLongToast(getString(R.string.enter_6_digit_pin_number))
        }
        else {
            openActivity(SecurityQuestionRegisterActivity::class.java) {
                putString(KEY_PIN, pinNumber)
                putString(KEY_CONFIRM_PIN, confirmPinNumber)
                putString(KEY_PASSWORD, password)
                putString(KEY_CONFIRM_PASSWORD, confirmPassword)

            }
        }
    }
}