package com.app.emcashmerchant.ui.transfer_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.ui.wallet.WalletFragment
import com.app.emcashmerchant.utils.*
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.*


class PerformTransferPaymentFragment : Fragment() {


    companion object {
        fun newInstance() = PerformTransferPaymentFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.qrCodeScannerFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
        return inflater.inflate(R.layout.fragment_perform_transfer_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var amount = requireArguments().getInt(KEY_AMOUNT)
        var senderPerson = requireArguments().getString(KEY_SENDER_NAME)
        var senderNumber = requireArguments().getString(KEY_SENDER_NUMBER)

        var qrCode = requireArguments().getString(KEY_REF_ID)

        tv_amount.text = amount.toString()
        tv_name.text = senderPerson
        tv_number.text = senderNumber
        tv_total_bill_amount.text = "Total Bill amount of ${amount.toString()} AED"

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.qrCodeScannerFragment)

        }
        btn_transfer.setOnClickListener {
            val bundle = bundleOf(

                KEY_REF_ID to qrCode


            )

            Navigation.findNavController(view).navigate(R.id.paymentPinNumberFragment, bundle)

        }

    }

}