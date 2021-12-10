package com.app.emcashmerchant.ui.convert_to_cash

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.WithDrawRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.convertEmcashTocash.viewModel.ConvertEmcashViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.*
import com.app.emcashmerchant.utils.locationhelper.LocationHelper
import kotlinx.android.synthetic.main.convert_to_cash_fragment.*
import kotlinx.android.synthetic.main.convert_to_cash_fragment.iv_back
import kotlinx.android.synthetic.main.dialog_emcash_successful.*
import timber.log.Timber

class ConvertToCashFragment : Fragment(R.layout.convert_to_cash_fragment) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }



    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ConvertEmcashViewModel::class.java)
        sessionStorage=SessionStorage(requireActivity())
        dialog = AppDialog(requireActivity())

        fetchLocation()
        viewModel.bankDetails()

        observe(view)

        iv_back.setOnClickListener {
            findNavController().popBackStack()

        }

        ll_addBank.setOnClickListener {
        findNavController().navigate(R.id.action_convertToCashFragment_to_addBankDetailsFragment)
        }

        btn_convert_emcash.setOnClickListener {
             amount = et_emcash.text.toString()

            if (gpsEnabled(requireActivity())) {

                if (viewModel.ibn.toString().isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.iban))

                } else if(amount.isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.enter_valid_amount))
                }else if(sessionStorage.balance?.toInt()!! < amount.toInt()){
                    requireActivity().showShortToast(getString(R.string.enough_bal))

                }
                else
                {
                    withdraw(amount, viewModel.ibn.toString())
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

            bankDetailsStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        viewModel.ibn=it.data?.data?.iBanNumber.toString()
                        tv_iban.text="Iban :".plus(it.data?.data?.iBanNumber)
                        tv_bank.text=it.data?.data?.branchName

                        if(it.data?.data==null){
                            ll_addBank.visibility=View.VISIBLE
                            ll_bankDetails.visibility=View.GONE
                            btn_convert_emcash.visibility=View.GONE

                        }else{

                            ll_addBank.visibility=View.GONE
                            ll_bankDetails.visibility=View.VISIBLE
                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)
                    }
                }
            })

            withDrawStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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