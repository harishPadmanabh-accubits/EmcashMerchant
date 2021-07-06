package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import kotlinx.android.synthetic.main.activity_create_password.*
import kotlinx.android.synthetic.main.activity_create_pin_number.*
import kotlinx.android.synthetic.main.activity_pin_number.*

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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun fetchPinNumber() {
        val pinnumber = fet_pin_number.getInput()
        val confirmPinNumber: String = fet_confirm_pin_number.getInput()

        if (pinnumber != confirmPinNumber) {
            showLongToast(getString(R.string.same_pin_number_validation))
        } else if (pinnumber.isEmpty() || confirmPinNumber.isEmpty()) {
            showLongToast(getString(R.string.please_fill_all_fields))
        } else {
            openActivity(SecurityQuestionRegisterActivity::class.java) {
                putString(KEY_PIN, pinnumber)
                putString(KEY_CONFIRM_PIN, confirmPinNumber)
                putString(KEY_PASSWORD, password)
                putString(KEY_CONFIRM_PASSWORD, confirmPassword)

            }
        }
    }
}