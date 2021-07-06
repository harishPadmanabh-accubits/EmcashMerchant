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
        val serviceDesc = et_service_description.text.toString()

        if (businessName.isNotEmpty() ||
            contactPersonName.isNotEmpty() ||
            tradeLicenceNumber.isNotEmpty() ||
            tradeLicenseAuthority.isNotEmpty()
        ){
            openActivity(BasicContactDetailsActivity::class.java){
                putString(KEY_BUISINESS_NAME,businessName)
                putString(KEY_CONTACT_PERSON,contactPersonName)
                putString(KEY_TRADE_LICENSE_NUM,tradeLicenceNumber)
                putString(KEY_LICENSE_AUTHORITY,tradeLicenseAuthority)
                putString(KEY_SERVICE_DESC,serviceDesc)
            }
        }else{
            showShortToast(getString(R.string.please_fill_all_fields))
        }




    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}