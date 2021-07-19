package com.app.emcashmerchant.ui.payment_request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.transfer_payment.TransferPaymentFragment
import com.app.emcashmerchant.utils.extensions.showKeyboard
import kotlinx.android.synthetic.main.fragment_transfer_payment.*
import timber.log.Timber


class PaymentRequestFragment : Fragment() {

    companion object {

        fun newInstance() =
            PaymentRequestFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
        return inflater.inflate(R.layout.fragment_payment_request, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_emcash.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Navigation.findNavController(view).navigate(R.id.paymentRequestQrCodeFragment)

            }
            false
        }

    }

    override fun onResume() {
        super.onResume()
        et_emcash.requestFocus()
        Timber.e("onResume")
        requireActivity().showKeyboard(et_emcash)
    }
}