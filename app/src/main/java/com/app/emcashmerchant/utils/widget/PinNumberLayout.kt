package com.app.emcashmerchant.utils.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.onDeletePressed
import com.app.emcashmerchant.utils.extensions.showKeyboard
import kotlinx.android.synthetic.main.fragment_transfer_payment_pin_number.*
import timber.log.Timber

class PinNumberLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var otpField1: EditText
    var otpField2: EditText
    var otpField3: EditText
    var otpField4: EditText
    var otpField5: EditText
    var otpField6: EditText


    init {
        ConstraintLayout.inflate(context, R.layout.layout_pin_number_fields, this)
        otpField1 = findViewById(R.id.et_otp_1)
        otpField2 = findViewById(R.id.et_otp_2)
        otpField3 = findViewById(R.id.et_otp_3)
        otpField4 = findViewById(R.id.et_otp_4)
        otpField5 = findViewById(R.id.et_otp_5)
        otpField6 = findViewById(R.id.et_otp_6)

        otpField1.requestFocus()
        handleFocusOnInput()




    }


    private fun handleFocusOnInput() {

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
                    otpField2.setCursorVisible(false)
                    otpField1.setBackgroundResource(R.drawable.circle_black_fill)

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
                    sb.deleteCharAt(0)
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
                    otpField3.setCursorVisible(false)
                    otpField2.setBackgroundResource(R.drawable.circle_black_fill)

                } else {
                    if (otpField2.length() > 0) {
                        otpField2.setText("")

                    } else if (otpField2.length() == 0) {
                        otpField2.clearFocus()
                        otpField1.requestFocus()

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

                    sb.deleteCharAt(0)

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
                    otpField4.setCursorVisible(false)
                    otpField3.setBackgroundResource(R.drawable.circle_black_fill)

                } else {
                    if (otpField3.length() > 0) {
                        otpField3.setText("")
                    } else if (otpField3.length() == 0) {
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

                    sb.deleteCharAt(0)

                }
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                if (sb.length == 0 && otpField4.length() == 1) {
                    sb.append(s)
                    otpField4.clearFocus()
                    otpField5.requestFocus()
                    otpField5.setCursorVisible(false)
                    otpField4.setBackgroundResource(R.drawable.circle_black_fill)

                } else {
                    if (otpField4.length() > 0) {
                        otpField4.setText("")
                    } else if (otpField4.length() == 0) {
                        otpField4.clearFocus()
                        otpField3.requestFocus()

                    }
                }
            }
        })
        otpField5.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

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

                if (sb.length == 0 && otpField5.length() == 1) {
                    sb.append(s)
                    otpField5.clearFocus()
                    otpField6.requestFocus()
                    otpField6.setCursorVisible(false)
                    otpField5.setBackgroundResource(R.drawable.circle_black_fill)

                } else {
                    if (otpField5.length() > 0) {
                        otpField5.setText("")
                    } else if (otpField5.length() == 0) {
                        otpField5.clearFocus()
                        otpField4.requestFocus()

                    }
                }
            }
        })
        otpField6.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

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
                if (sb.length == 0 && otpField6.length() == 1) {
                    sb.append(s)
                    otpField6.setBackgroundResource(R.drawable.circle_black_fill)

                } else {
                    if (otpField6.length() > 0) {
                        otpField6.setText("")

                    } else if (otpField6.length() == 0) {
                        otpField6.clearFocus()
                        otpField5.requestFocus()

                    }
                }
            }
        })

    }

    fun obtainOTP(): String {
        return otpField1.text.toString()
            .plus(otpField2.text.toString())
            .plus(otpField3.text.toString())
            .plus(otpField4.text.toString())
            .plus(otpField5.text.toString())
            .plus(otpField6.text.toString())
    }


}