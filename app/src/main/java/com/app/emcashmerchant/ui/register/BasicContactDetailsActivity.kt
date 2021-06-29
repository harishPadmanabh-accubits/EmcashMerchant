package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_basic_contact_details.*
import timber.log.Timber

class BasicContactDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    val buisinessName by lazy {
        intent.getStringExtra(KEY_BUISINESS_NAME)
    }
    val contactPersonName  by lazy {
        intent.getStringExtra(KEY_CONTACT_PERSON)
    }
    val tradeLicenceNumber  by lazy {
        intent.getStringExtra(KEY_TRADE_LICENSE_NUM)
    }
    val tradeLicenseAuthority by lazy {
        intent.getStringExtra(KEY_LICENSE_AUTHORITY)
    }
    val serviceDesc by lazy {
        intent.getStringExtra(KEY_SERVICE_DESC)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_contact_details)
        initViewModel()
        setupObservers()


    }

    private fun setupObservers() {
        viewModel.apply {

            initialSignupStatus.observe(this@BasicContactDetailsActivity, Observer {
                when(it.status){
                    ApiCallStatus.LOADING->{
                        //show loading
                    }
                    ApiCallStatus.SUCCESS->{
                        val data = it.data
                        showShortToast("success")
                    }
                    ApiCallStatus.ERROR->{
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
        when (view.id) {
            R.id.btn_confirm -> {
                Timber.e("${et_phone_num.obtainPhoneNumber() } isvalid ${PhoneNumberUtils.isGlobalPhoneNumber(et_phone_num.obtainPhoneNumber())}")
                if (et_phone_num.obtainPhoneNumber() != null)

                    //pass data from edittext here

                    performInitialSignup(
                        "Test buisness name",
                        "Test name",
                        "shyam@accubitsss.com",
                        "test fn",
                        "test ln",
                        "+9181368185338989",
                        "xyx",
                        "xyz",
                        "xyz",
                        "xyz",
                        "695038",
                        ""
                    )
                else {
                    showShortToast(getString(R.string.mobile_number_error))
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
        contactPersonName: String,
        email: String,
        firstName: String,
        lastName: String,
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
            contactPersonName,
            email,
            firstName,
            lastName,
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
}