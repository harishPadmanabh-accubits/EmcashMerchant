package com.app.emcashmerchant.ui.transfer_payment

import android.os.Bundle
import android.se.omapi.Session
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.IntiateContactPaymentRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.KEY_REF_ID
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.extensions.isNull
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.*

class PerformTransferByContactFragment : Fragment() {

    private lateinit var viewModel: TransferPaymentViewModel
    private lateinit var dialog: AppDialog
    private lateinit var sessionStorage:SessionStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.transferContactListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        return inflater.inflate(R.layout.fragment_perform_transfer_by_contact, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = AppDialog(requireActivity())
        viewModel = ViewModelProvider(this).get(TransferPaymentViewModel::class.java)
        sessionStorage= SessionStorage(requireActivity())
        var userId = requireArguments().getString(KEY_USERID).toString()


        viewModel.getOneContact(userId.toString())
        observer(view)

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.transferContactListFragment)

        }
        btn_transfer.setOnClickListener {
            var amount = et_amount.text.toString()
            var description = et_description.text.toString()

            if (amount.isEmpty()) {
                requireActivity().showShortToast(getString(R.string.enter_valid_amount))
            }else if(sessionStorage.balance?.toInt()!! < amount.toInt()){
                requireActivity().showShortToast(getString(R.string.enough_bal))

            }

            else {
                var intiateContactPaymentRequest = IntiateContactPaymentRequest(
                    amount.toInt(),
                    description,
                    userId.toInt()
                )
                viewModel.intiatePayment(intiateContactPaymentRequest)
            }

        }

    }

    fun observer(view: View) {
        viewModel.apply {
            oneContactStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        tv_name.text = it.data?.name
                        tv_number.text = it.data?.phoneNumber

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(it.errorMessage)

                    }
                }
            })
            intiatePaymentStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val bundle = bundleOf(
                            KEY_REF_ID to it.data?.referenceId
                        )

                        Navigation.findNavController(view)
                            .navigate(R.id.paymentPinNumberFragment, bundle)

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