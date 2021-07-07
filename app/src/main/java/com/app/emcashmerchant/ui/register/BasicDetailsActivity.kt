package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_basic_details.*

class BasicDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_details)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_confirm -> {
                gotoContactDetails()
            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    private fun gotoContactDetails() {
        val businessName = et_business_name.text.toString()
        val contactPersonName = et_contact_person_name.text.toString()
        val tradeLicenceNumber = et_trade_license_number.text.toString()
        val tradeLicenseAuthority = et_license_issue_authority.text.toString()
        var serviceDesc = et_service_description.text.toString()

        if (businessName.isEmpty()) {

            showShortToast(getString(R.string.valid_business_name))

        } else if (contactPersonName.isEmpty() || contactPersonName.length<3) {
            showShortToast(getString(R.string.valid_contact_personname))

        } else if (tradeLicenceNumber.isEmpty()) {
            showShortToast(getString(R.string.valid_tradeliscencenumber))

        } else if (tradeLicenseAuthority.isEmpty()) {
            showShortToast(getString(R.string.valid_trade_lic_auth))

        } else {
            if (serviceDesc.isEmpty()) {
                serviceDesc = ""
            }
            openActivity(BasicContactDetailsActivity::class.java) {
                putString(KEY_BUISINESS_NAME, businessName)
                putString(KEY_CONTACT_PERSON, contactPersonName)
                putString(KEY_TRADE_LICENSE_NUM, tradeLicenceNumber)
                putString(KEY_LICENSE_AUTHORITY, tradeLicenseAuthority)
                putString(KEY_SERVICE_DESC, serviceDesc)
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}