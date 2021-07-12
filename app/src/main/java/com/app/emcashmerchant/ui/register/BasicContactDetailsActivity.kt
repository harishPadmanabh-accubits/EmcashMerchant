package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.*
import com.app.emcashmerchant.Authviewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_basic_contact_details.*

class BasicContactDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog:AppDialog

    private val businessName by lazy {
        intent.getStringExtra(KEY_BUISINESS_NAME)
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
        initViews()
        initViewModel()
        setupObservers()
        dialog= AppDialog(this)



    }


    private fun setupObservers() {
        viewModel.apply {

            initialSignupStatus.observe(this@BasicContactDetailsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val data = it.data
                        sessionStorage.referenceIdInitial=data?.referenceId
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

    private fun initViewModel() {
        viewModel = obtainViewModel(RegisterViewModel::class.java)
    }

    fun onClick(view: View) {
        var address = et_address.text.toString()
        var email = et_email.text.toString()
        var phoneNumber = et_phone_num.obtainPhoneNumber().toString()
        var pincode = et_pin.text.toString()

        when (view.id) {
            R.id.btn_confirm -> {
                if (phoneNumber.length < 8) {
                    showShortToast(getString(R.string.enter_valid_number))
                } else if (!email.isEmailValidity()) {
                    showShortToast(getString(R.string.enter_valid_email))
                } else if (phoneNumber.isEmpty() ||
                    et_email.text.toString().isEmpty() ||
                    et_address.text.toString().isEmpty()
                ) {
                    showShortToast(getString(R.string.please_fill_all_fields))
                }
                else if(address.length<5){
                    showShortToast(getString(R.string.valid_address))
                }
                else {
                    if(pincode.isEmpty())
                    {
                        pincode=""
                    }
                    performInitialSignup(
                        address,
                        businessName.toString(),
                        email,
                        contactPersonName.toString(),
                        phoneNumber,
                        businessName.toString(),
                        serviceDesc.toString(),
                        tradeLicenseAuthority.toString(),
                        tradeLicenceNumber.toString(),
                        pincode
                    )
                }
            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    private fun performInitialSignup(
        address: String,
        businessName: String,
        email: String,
        contactPersonName: String,
        phoneNumber: String,
        registeredNameOfBusiness: String,
        serviceDescription: String,
        tradeLicenseIssuingAuthority: String,
        tradeLicenseNumber: String,
        zipCode: String
    ) {


        viewModel.performInitialSignup(
            address,
            businessName,
            email,
            contactPersonName.toString(),
            phoneNumber,
            registeredNameOfBusiness,
            serviceDescription,
            tradeLicenseIssuingAuthority,
            tradeLicenseNumber,
            zipCode
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }
}