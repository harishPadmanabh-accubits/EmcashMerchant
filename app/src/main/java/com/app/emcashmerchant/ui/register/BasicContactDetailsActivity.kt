package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.*
import com.app.emcashmerchant.ui.register.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_basic_contact_details.*

class BasicContactDetailsActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()
    private val sessionStorage by lazy {
        SessionStorage(this)
    }
    private val dialog by lazy {
        AppDialog(this)
    }

    private val businessName by lazy {
        intent.getStringExtra(KEY_BUISINESS_NAME)
    }
    private val registeredNameOfBusiness by lazy {
        intent.getStringExtra(KEY_REGISTERED_BUISINESS_NAME)
    }

    private val contactPersonName by lazy {
        intent.getStringExtra(KEY_CONTACT_PERSON)
    }
    private val tradeLicenceNumber by lazy {
        intent.getStringExtra(KEY_TRADE_LICENSE_NUM)
    }
    private val tradeLicenseAuthority by lazy {
        intent.getStringExtra(KEY_LICENSE_AUTHORITY)
    }
    private val serviceDesc by lazy {
        intent.getStringExtra(KEY_SERVICE_DESC)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_contact_details)

        setupObservers()


    }


    private fun setupObservers() {
        viewModel.apply {

            initialSignupStatus.observe(this@BasicContactDetailsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val data = it.data
                        sessionStorage.referenceIdInitial = data?.referenceId
                        sessionStorage.merchantNumber = et_phone_num.obtainPhoneNumber().toString()
                        openActivity(RegisterOtpActivity::class.java)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })
        }
    }


    fun onClick(view: View) {
        val address = et_address.text.toString()
        val email = et_email.text.toString()
        val phoneNumber = et_phone_num.obtainPhoneNumber().toString()
        val phoneNumberWithoutCode = et_phone_num.getPhoneNumberWithoutCode()
        var pinCode = et_pin.text.toString()

        when (view.id) {
            R.id.btn_confirm -> {
                if (!email.isEmailValidity()) {
                    showShortToast(getString(R.string.enter_valid_email))
                } else if (phoneNumberWithoutCode?.isValidPhoneNumber() != true) {
                    showShortToast(getString(R.string.enter_valid_number))
                } else if (phoneNumber.isEmpty() ||
                    et_email.text.toString().isEmpty() ||
                    et_address.text.toString().isEmpty()
                ) {
                    showShortToast(getString(R.string.please_fill_all_fields))
                } else if (address.length < 5) {
                    showShortToast(getString(R.string.valid_address))
                } else {
                    if (pinCode.isEmpty()) {
                        pinCode = ""
                    }

                    viewModel.performInitialSignup(
                        address,
                        businessName.toString(),
                        email,
                        contactPersonName.toString(),
                        phoneNumber,
                        registeredNameOfBusiness.toString(),
                        serviceDesc.toString(),
                        tradeLicenseAuthority.toString(),
                        tradeLicenceNumber.toString(),
                        pinCode, sessionStorage.referenceIdInitial
                    )

                }
            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }


}