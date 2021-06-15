package com.app.emcashmerchant.utils.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.hide
import com.app.emcashmerchant.utils.extensions.show
import kotlinx.android.synthetic.main.form_texttfield.view.*

class FormEditText(context: Context, attrs: AttributeSet) : ConstraintLayout(context,attrs) {

     lateinit var formInput : AppCompatEditText
     lateinit var actionFrame : FrameLayout
     lateinit var actionImg : AppCompatImageView

    enum class FormType{
        EMAIL,PASSWORD
    }

    var formType = FormType.EMAIL

    init {

        inflate(context, R.layout.form_texttfield, this)
        formInput = findViewById<AppCompatEditText>(R.id.et_form_input)
        actionFrame = findViewById<FrameLayout>(R.id.fl_form_action)
        actionImg = findViewById<AppCompatImageView>(R.id.iv_checked_green_round)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FormTextField)
        val array = context.obtainStyledAttributes(attrs, R.styleable.FormTextField, 0, 0)
        formType = FormType.values()[array.getInt(R.styleable.FormTextField_form_type, 0)]
        Log.e("hhp formtupe", "......$formType")
        Log.e("hhp formtupe", " draw......$formType")

        when (formType) {
            FormType.EMAIL -> {
                et_form_input.apply {
                    hint = context.getString(R.string.email)
                    inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }

            }
            FormType.PASSWORD -> {
                et_form_input.apply {
                    hint = context.getString(R.string.password)
                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                }

            }

        }
    }



    fun getText()=et_form_input.text.toString()

    fun showCheckMark() = iv_checked_green_round . show()

    fun hideCheckMark() = iv_checked_green_round .hide()




}