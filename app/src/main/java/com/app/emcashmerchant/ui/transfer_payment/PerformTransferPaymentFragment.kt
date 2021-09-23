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
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import com.app.emcashmerchant.utils.widget.LevelProfileImageView
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.*
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.*
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.btn_transfer
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.fl_user_level
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.iv_back
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.tv_name
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.tv_number


class PerformTransferPaymentFragment : Fragment(R.layout.fragment_perform_transfer_payment) {


    companion object {
        fun newInstance() = PerformTransferPaymentFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.qrCodeScannerFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var amount = requireArguments().getInt(KEY_AMOUNT)
        var senderPerson = requireArguments().getString(KEY_SENDER_NAME)
        var senderNumber = requireArguments().getString(KEY_SENDER_NUMBER)
        var profileImage = requireArguments().getString(KEY_PROFLE_IMAGE_LINK)

        var qrCode = requireArguments().getString(KEY_REF_ID)
        var userLevel = requireArguments().getString(KEY_LEVEL_COLOUR)?.toInt()
        var role = requireArguments().getString(KEY_ROLE)?.toInt()


        if (profileImage != null) {
            iv_userImage.loadImageWithUrl(profileImage.toString())
            iv_userImage.visibility = View.VISIBLE
            tv_firstLetter.visibility = View.INVISIBLE


        } else {
            tv_firstLetter.text = senderPerson.toString()[0].toString()
            iv_userImage.visibility = View.INVISIBLE
            tv_firstLetter.visibility = View.VISIBLE
            fl_user_level.setBackgroundResource(R.drawable.greyfilled_round)
            tv_firstLetter.setTextColor(resources.getColor(R.color.white))
        }

        if (role == 3) {
            if (userLevel == 1) {
                fl_user_level.setBackgroundResource(R.drawable.green_round)
            } else if (userLevel == 2) {
                fl_user_level.setBackgroundResource((R.drawable.yellow_round))
            } else if (userLevel == 4) {
                fl_user_level.setBackgroundResource(R.drawable.red_round)

            }
        } else if (role == 2) {


        }

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