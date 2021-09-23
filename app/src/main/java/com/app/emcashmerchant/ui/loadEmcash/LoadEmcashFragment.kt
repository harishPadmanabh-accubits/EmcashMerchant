package com.app.emcashmerchant.ui.loadEmcash

import android.content.pm.ActivityInfo
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.PaymentChatHistory.PaymentChatHistoryViewModel
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.gpsEnabled
import com.app.emcashmerchant.utils.extensions.showKeyboard
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.locationhelper.LocationHelper
import kotlinx.android.synthetic.main.fragment_load_emcash.*
import timber.log.Timber


  class LoadEmcashFragment : Fragment(R.layout.fragment_load_emcash) {

    lateinit var dialog: AppDialog
    lateinit var loadEmcashViewModel: LoadEmcashViewModel

    companion object {
        fun newInstance() =
            LoadEmcashFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            //                findNavController().navigate(R.id.walletFragment)

            findNavController().popBackStack()

        }

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
        loadEmcashViewModel = ViewModelProvider(this).get(LoadEmcashViewModel::class.java)

        fetchLocation()

        iv_back.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.walletFragment)
            findNavController().popBackStack()

        }
        fbtn_convert.setOnClickListener {
            loadEmcashViewModel.amount = et_cash.text.toString()
            loadEmcashViewModel.description = et_description.text.toString()
            if (gpsEnabled(requireActivity())) {
                if (loadEmcashViewModel.amount.toString().isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.enter_valid_amount))
                }else {
                    topUp(loadEmcashViewModel.amount.toString().toInt(), loadEmcashViewModel.description.toString(), view)
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
                    loadEmcashViewModel.latitude = location.latitude
                    loadEmcashViewModel.longitude = location.longitude



                }
            })
    }


    private fun topUp(amount: Int, description: String, view: View) {
        val bundle = bundleOf(
            KEY_AMOUNT to amount,
            KEY_DESCRIPTION to description,
            KEY_LATITUDE to loadEmcashViewModel.latitude,
            KEY_LONGITUDE to loadEmcashViewModel.longitude
        )
        Navigation.findNavController(view).navigate(R.id.transactionFragment, bundle)

    }


}