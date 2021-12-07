package com.app.emcashmerchant.ui.paymentRequest

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.KEY_AMOUNT
import com.app.emcashmerchant.utils.KEY_DESCRIPTION
import com.app.emcashmerchant.utils.KEY_QRCODE
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_payment_request_qr_code.*


class PaymentRequestQrCodeFragment : Fragment(R.layout.fragment_payment_request_qr_code) {

    companion object {
        fun newInstance() =
            PaymentRequestQrCodeFragment()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.paymentRequestFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var qrCode = requireArguments().getString(KEY_QRCODE).toString()

        tv_close.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.paymentRequestFragment)

        }
        bt_mobilenumber.setOnClickListener {
            var amount = requireArguments().getString(KEY_AMOUNT).toString()
            var description = requireArguments().getString(KEY_DESCRIPTION).toString()

            val bundle = bundleOf(
                KEY_AMOUNT to amount,
                KEY_DESCRIPTION to description,
                KEY_QRCODE to qrCode

            )
            Navigation.findNavController(view).navigate(R.id.requestContactListFragment, bundle)

        }

        Glide.with(requireContext())
            .load(qrCode)
            .into(iv_qr_code);
    }


}