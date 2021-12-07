package com.app.emcashmerchant.ui.transferPayment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.IntiateContactPaymentRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.*
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.btn_transfer
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.fl_user_level
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.iv_back
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.tv_name
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.tv_number

class TransferByContactFragment : Fragment(R.layout.fragment_perform_transfer_by_contact) {

    private lateinit var viewModel: TransferPaymentViewModel
    private lateinit var dialog: AppDialog
    private lateinit var sessionStorage: SessionStorage
    var navigateToScreen: String? = null
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (navigateToScreen == SCREEN_CHAT) {
                findNavController().popBackStack()

            } else {
                findNavController().navigate(R.id.transferContactListFragment)

            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = AppDialog(requireActivity())
        viewModel = ViewModelProvider(this).get(TransferPaymentViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        userId = requireArguments().getString(KEY_USERID).toString()
        navigateToScreen = requireArguments().getString(KEY_PAGE).toString()

        et_amount.requestFocus()
        viewModel.getOneContact(userId.toString())
        observer(view)

        iv_back.setOnClickListener {
            if (navigateToScreen == SCREEN_CHAT) {
                findNavController().popBackStack()

            } else {
                Navigation.findNavController(view).navigate(R.id.transferContactListFragment)

            }

        }
        btn_transfer.setOnClickListener {
            var amount = et_amount.text.toString()
            var description = et_description.text.toString()

            if (amount.isEmpty()) {
                requireActivity().showShortToast(getString(R.string.enter_valid_amount))
            } else if (sessionStorage.balance?.toInt()!! < amount.toInt()) {
                requireActivity().showShortToast(getString(R.string.enough_bal))

            } else {
                var intiateContactPaymentRequest = IntiateContactPaymentRequest(
                    amount.toInt(),
                    description,
                    userId.toString().toInt()
                )
                viewModel.initiatePayment(intiateContactPaymentRequest)
            }

        }

    }

    fun observer(view: View) {
        viewModel.apply {
            oneContactStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        tv_name.text = it.data?.name
                        tv_number.text = it.data?.phoneNumber
                        var userLevel = it.data?.ppp
                        var roleId = it.data?.roleId


                        if (it.data?.profileImage != null) {
                            iv_userImagee.loadImageWithUrl(it.data?.profileImage.toString())
                            iv_userImagee.visibility = View.VISIBLE
                            ll_tvHolder.visibility = View.INVISIBLE

                        } else {
                            tv_firstLetterr.text = it.data?.name.toString()[0].toString()
                            iv_userImagee.visibility = View.INVISIBLE
                            ll_tvHolder.visibility = View.VISIBLE
                            ll_tvHolder.setBackgroundResource(R.drawable.greyfilled_round)
                            tv_firstLetterr.setTextColor(resources.getColor(R.color.white))

                        }

                        if (roleId == 3) {
                            if (userLevel == 1) {
                                fl_user_level.setBackgroundResource(R.drawable.green_round)
                            } else if (userLevel == 2) {
                                fl_user_level.setBackgroundResource((R.drawable.yellow_round))
                            } else if (userLevel == 4) {
                                fl_user_level.setBackgroundResource(R.drawable.red_round)

                            }
                        } else if (roleId == 2) {


                        }


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(it.errorMessage)

                    }
                }
            })
            intiatePaymentStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        val bundle = bundleOf(
                            KEY_REF_ID to it.data?.referenceId,
                            KEY_PAGE to navigateToScreen,
                            KEY_USERID to userId.toString()

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