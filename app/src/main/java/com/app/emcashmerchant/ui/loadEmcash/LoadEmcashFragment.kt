package com.app.emcashmerchant.ui.loadEmcash

import android.content.pm.ActivityInfo
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.gpsEnabled
import com.app.emcashmerchant.utils.extensions.showKeyboard
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.locationhelper.LocationHelper
import kotlinx.android.synthetic.main.fragment_load_emcash.*
import timber.log.Timber


class LoadEmcashFragment : Fragment() {

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    lateinit var dialog: AppDialog

    companion object {
        fun newInstance() =
            LoadEmcashFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.walletFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return inflater.inflate(R.layout.fragment_load_emcash, container, false)
    }

    override fun onResume() {
        super.onResume()
        et_cash.requestFocus()
        Timber.e("onResume")
        requireActivity().showKeyboard(et_cash)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        fetchLocation()

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.walletFragment)
        }
        fbtn_convert.setOnClickListener {
            val amount: String = et_cash.text.toString()
            val description: String = et_description.text.toString()
            if (gpsEnabled(requireActivity())) {
                if (amount.isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.enter_valid_amount))
                } else {
                    topUp(amount.toInt(), description, view)
                }
            } else {
                requireActivity().showShortToast("Gps Off")
            }
        }

    }

    private fun fetchLocation() {
        LocationHelper().startListeningUserLocation(
            requireActivity(),
            object : LocationHelper.MyLocationListener {
                override fun onLocationChanged(location: Location) {
                    // Here you got user location :)
                    latitude = location.latitude
                    longitude = location.longitude
                }
            })
    }


    private fun topUp(amount: Int, description: String, view: View) {
        val bundle = bundleOf(
            KEY_AMOUNT to amount,
            KEY_DESCRIPTION to description,
            KEY_LATITUDE to latitude,
            KEY_LONGITUDE to longitude
        )
        Navigation.findNavController(view).navigate(R.id.transactionFragment, bundle)

    }

    override fun onPause() {
        super.onPause()

    }


}