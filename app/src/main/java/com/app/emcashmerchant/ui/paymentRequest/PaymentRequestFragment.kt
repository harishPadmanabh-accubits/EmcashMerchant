package com.app.emcashmerchant.ui.paymentRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.GenerateQrCodeRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.paymentRequest.viewModel.PaymentRequestViewModel
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.showKeyboard
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_payment_request.*

import timber.log.Timber


class PaymentRequestFragment : Fragment(R.layout.fragment_payment_request) {
    private lateinit var viewModel: PaymentRequestViewModel
    private lateinit var dialog: AppDialog
    var amount: String=""
    var description: String? = ""

    companion object {

        fun newInstance() =
            PaymentRequestFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentRequestViewModel::class.java)
        dialog = AppDialog(requireActivity())

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }

        btn_request.setOnClickListener {
            amount = et_emcash.text.toString()
            description = et_description.text.toString()

            if (amount.isEmpty()) {
                requireActivity().showShortToast(getString(R.string.enter_valid_amount))
            } else {
                var generateQrCodeRequest =
                    GenerateQrCodeRequest(amount.toInt(), description.toString())
                viewModel.generateQrCode(generateQrCodeRequest)

            }
        }
        et_emcash.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                amount = et_emcash.text.toString()
                description = et_description.text.toString()

                if (amount.isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.enter_valid_amount))
                } else {
                    var generateQrCodeRequest =
                        GenerateQrCodeRequest(amount.toInt(), description.toString())
                    viewModel.generateQrCode(generateQrCodeRequest)

                }

            }
            false
        }


        et_description.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                amount = et_emcash.text.toString()
                description = et_description.text.toString()

                if (amount.isEmpty()) {
                    requireActivity().showShortToast(getString(R.string.enter_valid_amount))
                } else {
                    var generateQrCodeRequest =
                        GenerateQrCodeRequest(amount.toInt(), description.toString())
                    viewModel.generateQrCode(generateQrCodeRequest)

                }

            }
            false
        }


        observe(view)


    }

    override fun onResume() {
        super.onResume()
        et_emcash.requestFocus()
        Timber.e("onResume")
        requireActivity().showKeyboard(et_emcash)
    }

    fun observe(view: View) {
        viewModel.apply {
            generateQrCodeStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val bundle = bundleOf(
                            KEY_AMOUNT to amount,
                            KEY_DESCRIPTION to description,
                            KEY_QRCODE to it.data?.qrCode


                        )
                        Navigation.findNavController(view)
                            .navigate(R.id.paymentRequestQrCodeFragment, bundle)


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(it.errorMessage)


                    }

                }
            })
        }
    }
}