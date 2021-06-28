package com.app.emcashmerchant.utils.widget

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import com.app.emcashmerchant.R
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.form_number_withcode.view.*

class PhoneNumberEditText(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    var phoneNumber = ""

    init {

        LinearLayout.inflate(context, R.layout.form_number_withcode, this)
    }

    fun obtainPhoneNumber(): String? {
        phoneNumber = picker_countrycode.selectedCountryCodeWithPlus.plus(et_number.text.toString())

        if (et_number.text.toString().isNotEmpty()) {
            return phoneNumber
        } else {
            return null
        }

    }


}
