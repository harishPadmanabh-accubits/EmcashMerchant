package com.app.emcashmerchant.ui.convertEmcashTocash

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.WithDrawRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.loadEmcash.LoadEmcashViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.gpsEnabled
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.locationhelper.LocationHelper
import kotlinx.android.synthetic.main.activity_convert_emcash_to_cash.*
import kotlinx.android.synthetic.main.dialog_emcash_successful.*


class ConvertEmcashToCashActivity : AppCompatActivity() {
    private lateinit var sessionStorage: SessionStorage
    private lateinit var viewModel: ConvertEmcashViewModel
    lateinit var dialog: AppDialog
    lateinit var dialogSuccess: Dialog
    var amount: String=""
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_emcash_to_cash)
        et_emcash.requestFocus();
        dialog = AppDialog(this)
        init()
        dialogSuccess()
        fetchLocation()
        getViewModel()
        observe()

    }

    fun onClick(view: View) {
         amount = et_emcash.text.toString()
        val ibn: String = et_enter_ibn.text.toString()
        when (view.id) {
            R.id.btn_convert -> {
                if (gpsEnabled(this)) {

             if (ibn.isEmpty()) {
                        showShortToast(getString(R.string.iban))

                    } else if(amount.isEmpty()) {
                        showShortToast(getString(R.string.enter_valid_amount))
                    }
                    else
                    {
                        withdraw(amount, ibn)
                    }
                } else {
                    showLongToast("Gps Off")
                }
            }
        }
    }

    private fun getViewModel() {
        viewModel = obtainViewModel(ConvertEmcashViewModel::class.java)
    }

    fun init() {
        sessionStorage = SessionStorage(this);

    }

    private fun observe() {
        viewModel.apply {
            withDrawStatus.observe(this@ConvertEmcashToCashActivity, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                        dialogSuccess.dismiss()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        dialogSuccess.show()
                        showShortToast(getString(R.string.emcash_loaded))

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        dialogSuccess.dismiss()
                        showShortToast(it.errorMessage)

                    }
                }

            })
        }
    }

    fun fetchLocation() {
        LocationHelper().startListeningUserLocation(
            this,
            object : LocationHelper.MyLocationListener {
                override fun onLocationChanged(location: Location) {
                    // Here you got user location :)
                    latitude = location.latitude
                    longitude = location.longitude
                }
            })
    }

    override fun onBackPressed() {
        finish()
    }

    private fun withdraw(amount: String, iban: String) {
        val withDrawRequest = WithDrawRequest(amount.toInt(), "", iban, latitude, longitude)
        viewModel.withDraw(withDrawRequest)
    }

    private fun dialogSuccess() {
        dialogSuccess = Dialog(this)
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccess.setContentView(R.layout.dialog_emcash_successful)
        dialogSuccess.setCancelable(false)
        dialogSuccess.setCanceledOnTouchOutside(false)
        dialogSuccess.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        tv_dialogmessage.text="${amount} EmCash has been successfully converted to cash."
//        btn_okay.setOnClickListener {
//            dialogSuccess.dismiss()
//            onBackPressed()
//        }
    }
}