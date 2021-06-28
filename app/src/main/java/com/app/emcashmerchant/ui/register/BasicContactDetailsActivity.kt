package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_basic_contact_details.*

class BasicContactDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_contact_details)


    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_confirm -> {
                if (et_phone_num.obtainPhoneNumber() != null)
                    openActivity(RegisterOtpActivity::class.java)
                else {
                    showShortToast(getString(R.string.mobile_number_error))
                }
            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}