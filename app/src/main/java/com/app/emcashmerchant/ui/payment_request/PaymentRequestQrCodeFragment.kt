package com.app.emcashmerchant.ui.payment_request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.emcashmerchant.R

class PaymentRequestQrCodeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_request_qr_code, container, false)
    }

    companion object {
        fun newInstance() =
            PaymentRequestQrCodeFragment()

    }
}