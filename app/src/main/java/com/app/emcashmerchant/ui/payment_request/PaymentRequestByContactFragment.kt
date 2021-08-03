package com.app.emcashmerchant.ui.payment_request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.PaymentRequest
import com.app.emcashmerchant.data.network.ApiCallStatus

import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.KEY_NAME
import com.app.emcashmerchant.utils.KEY_NUMBER

import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.*


class PaymentRequestByContactFragment : Fragment() {
    private lateinit var viewModel: PaymentRequestViewModel
    private lateinit var dialog: AppDialog
    var userId: String = ""
    var name: String? = null
    var phoneNumber: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)


        return inflater.inflate(R.layout.fragment_payment_request_by_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PaymentRequestViewModel::class.java)
        dialog = AppDialog(requireActivity())
        userId = requireArguments().getString(KEY_USERID).toString()

        name = requireArguments().getString(KEY_NAME).toString()
        phoneNumber = requireArguments().getString(KEY_NUMBER).toString()

        tv_name.text=name
        tv_number.text=phoneNumber
        iv_back.setOnClickListener {
            findNavController().popBackStack()

        }
        btn_request.setOnClickListener {
            var amount = et_amount.text.toString()
            var description = et_description.text.toString()

            if (amount.isEmpty()) {
                requireActivity().showShortToast(getString(R.string.enter_valid_amount))

            }
            val paymentRequest = PaymentRequest(amount.toInt(), description.toString(), userId.toInt())
            viewModel.paymentRequest(paymentRequest)
        }
        observe(view)

    }

    fun observe(view: View) {
        viewModel.apply {

            paymentRequestStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        var bundle = bundleOf(
                            KEY_USERID to userId

                        )
                        findNavController().navigate(
                            R.id.action_paymentRequestByContactFragment_to_paymentChatHistoryFragment,
                            bundle
                        )


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()

                    }

                }
            })
        }
    }


}