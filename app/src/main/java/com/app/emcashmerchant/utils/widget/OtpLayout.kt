package com.app.emcashmerchant.utils.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.emcashmerchant.R

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
//        otpField1.afterTextChanged {
//            otpField2.requestFocus()
//        }
//
//        otpField2.afterTextChanged {
//            otpField3.requestFocus()
//        }
//
//        otpField3.afterTextChanged {
//            otpField4.requestFocus()
//        }
//
//        otpField4.onDeletePressed(otpField3)
//        otpField3.onDeletePressed(otpField2)
//        otpField2.onDeletePressed(otpField1)

        val sb = StringBuilder()
        otpField1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                if (sb.length == 1) {
                    sb.deleteCharAt(0)
                }
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                if (sb.length == 0 && otpField1.length() === 1) {
                    sb.append(s)
                    otpField1.clearFocus()
                    otpField2.requestFocus()
                    otpField2.setCursorVisible(true)
                }
            }
        })
        otpField2.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                if (sb.length == 1) {
                    sb.deleteCharAt(0);
                }
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (sb.length == 0 && otpField2.length() === 1) {
                    sb.append(s)
                    otpField2.clearFocus()
                    otpField3.requestFocus()
                    otpField3.setCursorVisible(true)
                } else {
                    if (otpField2.length() > 0) {
                        otpField2.setText("");
                    } else if (otpField2.length() == 0) {
                        otpField2.clearFocus();
                        otpField1.requestFocus();
                    }
                }
            }
        })
        otpField3.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

                if (sb.length == 1) {

                    sb.deleteCharAt(0);

                }
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                if (sb.length == 0 && otpField3.length() == 1) {
                    sb.append(s)
                    otpField3.clearFocus()
                    otpField4.requestFocus()
                    otpField4.setCursorVisible(true)
                } else {
                    if (otpField3.length() > 0) {
                        otpField3.setText("")
                    } else if (otpField3.length() === 0) {
                        otpField3.clearFocus()
                        otpField2.requestFocus()
                    }
                }
            }
        })
        otpField4.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

                if (sb.length == 1) {

                    sb.deleteCharAt(0);
                }
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (sb.length == 0 && otpField4.length() == 1) {
                    sb.append(s);

                } else {
                    if (otpField4.length() > 0) {
                        otpField4.setText("");
                    }
                    else if (otpField4.length() == 0) {
                        otpField4.clearFocus();
                        otpField3.requestFocus();
                    }
                }
            }
        })

    }

    fun obtainOTP() = otpField1.text.toString()
        .plus(otpField2.text.toString())
        .plus(otpField3.text.toString())
        .plus(otpField4.text.toString())

}