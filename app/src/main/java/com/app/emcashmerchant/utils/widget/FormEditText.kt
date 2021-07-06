package com.app.emcashmerchant.utils.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.form_texttfield.view.*


@SuppressLint("Recycle", "CustomViewStyleable")
class FormEditText(context: Context, attrs: AttributeSet) : ConstraintLayout(context,attrs) {

      var formInput : AppCompatEditText
      var actionFrame : FrameLayout
      var actionImg : AppCompatImageView
      var isValidEmail = false

    enum class FormType{
        EMAIL,PASSWORD,PIN,NEW_PASSWORD,RE_ENTER_PASSWORD,PIN_NUMBER,CONFIRM_PIN_NUMBER
    }

    var formType = FormType.EMAIL

    init {

        inflate(context, R.layout.form_texttfield, this)
        formInput = findViewById(R.id.et_form_input)
        actionFrame = findViewById(R.id.fl_form_action)
        actionImg = findViewById(R.id.iv_form_action_image)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FormTextField)
        val array = context.obtainStyledAttributes(attrs, R.styleable.FormTextField, 0, 0)
        formType = FormType.values()[array.getInt(R.styleable.FormTextField_form_type, 0)]

        when (formType) {
            FormType.EMAIL -> {
                et_form_input.apply {
                    hint = context.getString(R.string.email)
                    inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }
                checkForValidEmail()


            }
            FormType.PASSWORD -> {
                var isPasswordVisible = false
                et_form_input.apply {
                    hint = context.getString(R.string.password)
                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
                iv_form_action_image.loadImageWithResId(R.drawable.ic_eye_hide)
                iv_form_action_image.show()
                iv_form_action_image.setOnClickListener {
                    if(isPasswordVisible){
                        setPasswordVisibilty(false)
                        isPasswordVisible=false
                    }else{
                        setPasswordVisibilty(true)
                        isPasswordVisible=true
                    }
                }


            }
            FormType.PIN->{
                et_form_input.apply {
                    hint = context.getString(R.string.pin)
                    inputType = InputType.TYPE_CLASS_NUMBER
                    maxLines = 1
                    filters = arrayOf<InputFilter>(LengthFilter(6))
                }
                checkForValidNumber()


            }
            FormType.NEW_PASSWORD->{
                var isPasswordVisible = false
                et_form_input.apply {
                    hint = context.getString(R.string.newPassword)
                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
                iv_form_action_image.loadImageWithResId(R.drawable.ic_eye_hide)
                iv_form_action_image.show()
                iv_form_action_image.setOnClickListener {
                    if(isPasswordVisible){
                        setPasswordVisibilty(false)
                        isPasswordVisible=false

                    }else{
                        setPasswordVisibilty(true)
                        isPasswordVisible=true
                    }
                }

            }
            FormType.RE_ENTER_PASSWORD->{
                var isPasswordVisible = false
                et_form_input.apply {
                    hint = context.getString(R.string.re_enter_password)
                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
                iv_form_action_image.loadImageWithResId(R.drawable.ic_eye_hide)
                iv_form_action_image.show()
                iv_form_action_image.setOnClickListener {
                    if(isPasswordVisible){
                        setPasswordVisibilty(false)
                        isPasswordVisible=false

                    }else{
                        setPasswordVisibilty(true)
                        isPasswordVisible=true
                    }
                }

            }
            FormType.PIN_NUMBER->{
                et_form_input.apply {
                    hint = context.getString(R.string.enter_pin_number)
                    inputType = InputType.TYPE_CLASS_NUMBER
                    maxLines = 1
                    filters = arrayOf<InputFilter>(LengthFilter(6))
                }
                checkForValidNumber()

            }
            FormType.CONFIRM_PIN_NUMBER->{
                et_form_input.apply {
                    hint = context.getString(R.string.confirm_pin_number)
                    inputType = InputType.TYPE_CLASS_NUMBER
                    maxLines = 1
                    filters = arrayOf<InputFilter>(LengthFilter(6))

                }
                checkForValidNumber()


            }


        }
    }
    private fun checkForValidEmail() {
        formInput.afterTextChanged { email->
            if(email.isEmailValid()){
                showCheckMark()
                isValidEmail = true
            }
            else{
                hideCheckMark()
                isValidEmail = false
            }
        }
    }

    private fun checkForValidNumber(){
        formInput.afterTextChanged {pin->
            if(pin.length==6){
                showCheckMark()
            }
            else
            {
                hideCheckMark()
            }
        }
    }

    fun  getInput()=et_form_input.text.toString()

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

    fun getFormInputEditText() = formInput





}