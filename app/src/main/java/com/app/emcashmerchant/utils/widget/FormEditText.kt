package com.app.emcashmerchant.utils.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.hide
import com.app.emcashmerchant.utils.extensions.loadImageWithResId
import com.app.emcashmerchant.utils.extensions.show
import kotlinx.android.synthetic.main.form_texttfield.view.*

@SuppressLint("Recycle", "CustomViewStyleable")
class FormEditText(context: Context, attrs: AttributeSet) : ConstraintLayout(context,attrs) {

      var formInput : AppCompatEditText
      var actionFrame : FrameLayout
      var actionImg : AppCompatImageView

    enum class FormType{
        EMAIL,PASSWORD
    }

    var formType = FormType.EMAIL

    init {

        inflate(context, R.layout.form_texttfield, this)
        formInput = findViewById<AppCompatEditText>(R.id.et_form_input)
        actionFrame = findViewById<FrameLayout>(R.id.fl_form_action)
        actionImg = findViewById<AppCompatImageView>(R.id.iv_form_action_image)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FormTextField)
        val array = context.obtainStyledAttributes(attrs, R.styleable.FormTextField, 0, 0)
        formType = FormType.values()[array.getInt(R.styleable.FormTextField_form_type, 0)]

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
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
                iv_form_action_image.loadImageWithResId(R.drawable.ic_eye_hide)
                iv_form_action_image.show()


            }

        }
    }

    fun getInput()=et_form_input.text.toString()

    fun showCheckMark() {
        iv_form_action_image.apply {
             setColorFilter(context.getColor(R.color.green))
             loadImageWithResId(R.drawable.ic_baseline_check_circle_24)
             show()
         }
    }

    fun hideCheckMark() {
        iv_form_action_image .apply {
            setColorFilter(null)
            hide()
        }
    }

    fun setPasswordVisibilty(isVisible:Boolean)=
        if(isVisible){
            et_form_input.transformationMethod = HideReturnsTransformationMethod.getInstance()
            iv_form_action_image.loadImageWithResId(R.drawable.ic_eye_hide)
        }
        else{
            et_form_input.transformationMethod = PasswordTransformationMethod.getInstance()
            iv_form_action_image.loadImageWithResId(R.drawable.ic_baseline_remove_red_eye_24)

        }





}