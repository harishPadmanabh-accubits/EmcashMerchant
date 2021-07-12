package com.app.emcashmerchant.ui.loadEmcash

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.gpsEnabled
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.locationhelper.LocationHelper
import kotlinx.android.synthetic.main.activity_load_emcash.*

import java.util.Observer

class LoadEmcashActivity : AppCompatActivity() {
    private lateinit var sessionStorage: SessionStorage
    private lateinit var viewModel: LoadEmcashViewModel
    var latitude:Double=0.0
    var longitude:Double=0.0
    lateinit var dialog: AppDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_emcash)
        et_cash.requestFocus();
        dialog= AppDialog(this)

        getViewModel()
        init()
        fetchLocation()
        observe()
    }


    fun onClick(view: View) {
        val  amount:Int?=et_cash.text.toString().toInt()
        val description:String=et_description.text.toString()
        when (view.id) {
            R.id.btn_convert -> {
                if (gpsEnabled(this)) {
                    if(amount!=null && amount!=0){
                        topUp(amount,description)
                    }
                    else{
                        showShortToast(getString(R.string.enter_valid_amount))
                    }
                } else {
                    showShortToast("Gps Off")
                }
            }
            R.id.ll_back -> {
                onBackPressed()
            }

        }
    }

    private fun getViewModel() {
        viewModel = obtainViewModel(LoadEmcashViewModel::class.java)
    }

    fun init() {
        sessionStorage = SessionStorage(this);
    }

    fun fetchLocation() {
        LocationHelper().startListeningUserLocation(
            this,
            object : LocationHelper.MyLocationListener {
                override fun onLocationChanged(location: Location) {
                    // Here you got user location :)
                    latitude=location.latitude
                    longitude=location.longitude
                }
            })
    }

    override fun onBackPressed() {
        finish()
    }

    private fun topUp(amount:Int,description:String) {

        val topUpRequest= TopUpRequest(amount,description,latitude,longitude)
            viewModel.topUp(topUpRequest)
    }

    private fun observe() {
        viewModel.apply {
            topupStatus.observe(this@LoadEmcashActivity, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                     dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        showShortToast(getString(R.string.emcash_loaded))

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)

                    }
                }

            })

        }
    }

}