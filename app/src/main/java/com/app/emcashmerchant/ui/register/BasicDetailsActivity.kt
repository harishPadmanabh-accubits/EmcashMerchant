package com.app.emcashmerchant.ui.register

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.emcashmerchant.R
import com.app.emcashmerchant.databinding.ActivityBasicDetailsBinding
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.setInputFilter
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_basic_details.*


class BasicDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasicDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInputFilters()
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

    private fun setInputFilters(){
        binding.etBusinessName.setInputFilter()
        binding.etContactPersonName.setInputFilter()
        binding.etTradeLicenseNumber.setInputFilter()
        binding.etLicenseIssueAuthority.setInputFilter()
        binding.etServiceDescription.setInputFilter()
        binding.etRegBusinessName.setInputFilter()
    }

    private fun gotoContactDetails() {
        val businessName = binding.etBusinessName.text.toString()
        val contactPersonName = binding.etContactPersonName.text.toString()
        val tradeLicenceNumber = binding.etTradeLicenseNumber.text.toString()
        val tradeLicenseAuthority = binding.etLicenseIssueAuthority.text.toString()
        var serviceDesc = binding.etServiceDescription.text.toString()
        val registeredBussinesName: String = binding.etRegBusinessName.text.toString()

        when {
            businessName.isEmpty() || businessName.isBlank() || businessName.length < 3 -> {

                showShortToast(getString(R.string.valid_business_name))

            }
            contactPersonName.isEmpty() || contactPersonName.isBlank() || contactPersonName.length < 3 -> {
                showShortToast(getString(R.string.valid_contact_personname))

            }
            registeredBussinesName.isEmpty() || registeredBussinesName.isBlank() || registeredBussinesName.length < 3 -> {
                showShortToast(getString(R.string.registered_name_validaiton))

            }
            tradeLicenceNumber.isEmpty() || tradeLicenceNumber.isBlank() || tradeLicenceNumber.length < 3 -> {
                showShortToast(getString(R.string.valid_tradeliscencenumber))

            }
            tradeLicenseAuthority.isEmpty() || tradeLicenseAuthority.isBlank() || tradeLicenseAuthority.length < 3 -> {
                showShortToast(getString(R.string.valid_trade_lic_auth))

            }
            else -> {
                if (serviceDesc.isEmpty()) {
                    serviceDesc = ""
                }
                openActivity(BasicContactDetailsActivity::class.java) {
                    putString(KEY_BUISINESS_NAME, businessName)
                    putString(KEY_REGISTERED_BUISINESS_NAME, registeredBussinesName)
                    putString(KEY_CONTACT_PERSON, contactPersonName)
                    putString(KEY_TRADE_LICENSE_NUM, tradeLicenceNumber)
                    putString(KEY_LICENSE_AUTHORITY, tradeLicenseAuthority)
                    putString(KEY_SERVICE_DESC, serviceDesc)
                }

            }
        }


    }


}