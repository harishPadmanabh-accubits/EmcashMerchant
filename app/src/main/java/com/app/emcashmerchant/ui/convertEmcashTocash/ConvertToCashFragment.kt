package com.app.emcashmerchant.ui.convert_to_cash

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.WithDrawRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.convertEmcashTocash.ConvertEmcashViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.gpsEnabled
import com.app.emcashmerchant.utils.extensions.showKeyboard
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.locationhelper.LocationHelper
import kotlinx.android.synthetic.main.convert_to_cash_fragment.btn_convert_emcash
import kotlinx.android.synthetic.main.convert_to_cash_fragment.et_emcash
import kotlinx.android.synthetic.main.convert_to_cash_fragment.et_description
import kotlinx.android.synthetic.main.convert_to_cash_fragment.iv_back
import kotlinx.android.synthetic.main.dialog_emcash_successful.*
import timber.log.Timber

class ConvertToCashFragment : Fragment() {
    private lateinit var sessionStorage: SessionStorage
    private lateinit var viewModel: ConvertEmcashViewModel
    lateinit var dialog: AppDialog
    lateinit var dialogSuccess: Dialog
    var amount: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    companion object {
        fun newInstance() =
            ConvertToCashFragment()
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
        val view: View = inflater.inflate(R.layout.convert_to_cash_fragment, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ConvertEmcashViewModel::class.java)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        dialog = AppDialog(requireActivity())

        fetchLocation()
        observe(view)

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.walletFragment)
        }
        btn_convert_emcash.setOnClickListener {
             amount = et_emcash.text.toString()
            val ibn: String = et_description.text.toString()
            if (gpsEnabled(requireActivity())) {

                if (ibn.isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.iban))

                } else if(amount.isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.enter_valid_amount))
                }
                else
                {
                    withdraw(amount, ibn)
                }
            } else {
                requireActivity().showLongToast("Gps Off")
            }
        }
    }
    private fun withdraw(amount: String, iban: String) {
        val withDrawRequest = WithDrawRequest(amount.toInt(), "", iban, latitude, longitude)
        viewModel.withDraw(withDrawRequest)
    }


    override fun onResume() {
        super.onResume()
        et_emcash.requestFocus()
        Timber.e("onResume")
        requireActivity().showKeyboard(et_emcash)
    }
    fun fetchLocation() {
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


    private fun observe(view: View) {
        viewModel.apply {
            withDrawStatus.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        dialogSuccess(amount,view)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(it.errorMessage)

                    }
                }

            })
        }
    }
    private fun dialogSuccess(amount: String,view: View) {
        dialogSuccess = Dialog(requireActivity())
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccess.setContentView(R.layout.dialog_emcash_successful)
        dialogSuccess.setCancelable(false)
        dialogSuccess.setCanceledOnTouchOutside(false)
        dialogSuccess.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogSuccess.show()
        dialogSuccess.tv_dialogmessage.text="${amount} EmCash has been successfully converted to cash."
        dialogSuccess.btn_okay.setOnClickListener {
            dialogSuccess.dismiss()
            Navigation.findNavController(view).navigate(R.id.walletFragment)
        }
    }
}