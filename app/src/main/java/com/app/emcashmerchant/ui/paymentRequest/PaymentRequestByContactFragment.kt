package com.app.emcashmerchant.ui.paymentRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.PaymentRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.paymentRequest.viewModel.PaymentRequestViewModel
import com.app.emcashmerchant.utils.*

import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.*
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.et_amount
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.et_description
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.fl_user_level
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.iv_back
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.tv_name
import kotlinx.android.synthetic.main.fragment_payment_request_by_contact.tv_number


class PaymentRequestByContactFragment : Fragment(R.layout.fragment_payment_request_by_contact) {
    private lateinit var viewModel: PaymentRequestViewModel
    private lateinit var dialog: AppDialog
    var userId: String = ""
    var name: String? = null
    var phoneNumber: String? = null
    var userLevel: Int? = 0
    var roleId: Int? = 0
    var profileImage:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PaymentRequestViewModel::class.java)
        dialog = AppDialog(requireActivity())
        userId = requireArguments().getString(KEY_USERID).toString()
        roleId = requireArguments().getString(KEY_ROLEID)?.toInt()
        userLevel = requireArguments().getString(KEY_USERLEVEL)?.toInt()
        profileImage = requireArguments().getString(KEY_PROFLE_IMAGE_LINK)

        name = requireArguments().getString(KEY_NAME).toString()
        phoneNumber = requireArguments().getString(KEY_NUMBER).toString()

        tv_name.text=name
        tv_number.text=phoneNumber

        if (profileImage != null) {
            iv_user_dp.loadImageWithUrl(profileImage.toString())
            iv_user_dp.visibility = View.VISIBLE
            ll_tvHolder.visibility = View.INVISIBLE


        } else {

            tv_firstLetter.text = name.toString()[0].toString()
            iv_user_dp.visibility = View.INVISIBLE
            ll_tvHolder.visibility = View.VISIBLE
            ll_tvHolder.setBackgroundResource(R.drawable.greyfilled_round)
            tv_firstLetter.setTextColor(resources.getColor(R.color.white))

        }

        if (roleId == 3) {
            if (userLevel == 1) {
                fl_user_level.setBackgroundResource(R.drawable.green_round)
            } else if (userLevel == 2) {
                fl_user_level.setBackgroundResource((R.drawable.yellow_round))
            } else if (userLevel == 4) {
                fl_user_level.setBackgroundResource(R.drawable.red_round)

            }
        }else if (roleId == 2) {


        }
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

            paymentRequestStatus.observe(viewLifecycleOwner, Observer {
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
                        requireActivity().showShortToast(it.errorMessage)
                        dialog.dismiss_dialog()

                    }

                }
            })
        }
    }


}