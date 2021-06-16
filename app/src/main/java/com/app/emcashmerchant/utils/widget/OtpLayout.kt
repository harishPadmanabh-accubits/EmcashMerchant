package com.app.emcashmerchant.utils.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.afterTextChanged
import com.app.emcashmerchant.utils.extensions.onDeletePressed

class OtpLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var otpField1: EditText
    var otpField2: EditText
    var otpField3: EditText
    var otpField4: EditText


    init {
        ConstraintLayout.inflate(context, R.layout.layout_otp_fields, this)
        otpField1 = findViewById(R.id.et_otp_1)
        otpField2 = findViewById(R.id.et_otp_2)
        otpField3 = findViewById(R.id.et_otp_3)
        otpField4 = findViewById(R.id.et_otp_4)

        otpField1.requestFocus()

        handleFocusOnInput()




    }

    private fun handleFocusOnInput() {
        otpField1.afterTextChanged {
            otpField2.requestFocus()
        }

        otpField2.afterTextChanged {
            otpField3.requestFocus()
        }

        otpField3.afterTextChanged {
            otpField4.requestFocus()
        }

//        otpField4.onDeletePressed(otpField3)
//        otpField3.onDeletePressed(otpField2)
//        otpField2.onDeletePressed(otpField1)


    }

    fun obtainOTP() = otpField1.text.toString()
        .plus(otpField2.text.toString())
        .plus(otpField2.text.toString())
        .plus(otpField3.text.toString())
        .plus(otpField4.text.toString())

}