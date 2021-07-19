package com.app.emcashmerchant.ui.transfer_payment

import android.accounts.AuthenticatorDescription
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.home.home_screen.HomeFragment
import com.app.emcashmerchant.ui.home.home_screen.HomeViewModel
import com.app.emcashmerchant.ui.wallet.WalletViewModel
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapter
import com.app.emcashmerchant.utils.KEY_AMOUNT
import com.app.emcashmerchant.utils.KEY_DESCRIPTION
import com.app.emcashmerchant.utils.KEY_LATITUDE
import com.app.emcashmerchant.utils.KEY_LONGITUDE
import com.app.emcashmerchant.utils.extensions.dateFormat
import com.app.emcashmerchant.utils.extensions.getCurrentDate
import com.app.emcashmerchant.utils.extensions.showKeyboard
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_transfer_payment.*
import kotlinx.android.synthetic.main.fragment_transfer_payment.iv_back
import kotlinx.android.synthetic.main.wallet_fragment.*
import timber.log.Timber


class TransferPaymentFragment : Fragment() {

    private lateinit var viewModel: TransferPaymentViewModel
    private lateinit var sessionStorage: SessionStorage


    companion object {

        fun newInstance() =
            TransferPaymentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return inflater.inflate(R.layout.fragment_transfer_payment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransferPaymentViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }
        et_emcash.setOnEditorActionListener { _, actionId, _ ->
            var amount: String = et_emcash.text.toString()
            var description: String = et_description.text.toString()

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                gotoQrCodeScanner(view, amount, description)
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        et_emcash.requestFocus()
        requireActivity().showKeyboard(et_emcash)
    }

    private fun gotoQrCodeScanner(view: View, amount: String, description: String) {
        if (amount.isEmpty()) {
            requireActivity().showShortToast(getString(R.string.enter_valid_amount))
        } else {
            val bundle = bundleOf(
                KEY_AMOUNT to amount,
                KEY_DESCRIPTION to description
            )
            Navigation.findNavController(view).navigate(R.id.qrCodeScannerFragment, bundle)

        }

    }


}