package com.app.emcashmerchant.ui.transfer_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.wallet.WalletFragment


class PerformTransferPaymentFragment : Fragment() {


    companion object {
        fun newInstance() = PerformTransferPaymentFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perform_transfer_payment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}