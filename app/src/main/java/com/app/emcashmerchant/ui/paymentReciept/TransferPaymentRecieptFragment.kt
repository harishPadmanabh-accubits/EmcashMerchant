package com.app.emcashmerchant.ui.paymentReciept

import android.os.Bundle
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
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.dateFormat
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.extensions.timeformat
import com.app.emcashmerchant.utils.extensions.trimID
import kotlinx.android.synthetic.main.fragment_transfer_payment_reciept.*
import kotlinx.android.synthetic.main.layout_payment_receipt_bottom.*
import kotlinx.android.synthetic.main.layout_payment_reciept_middle.*
import kotlinx.android.synthetic.main.layout_payment_reciept_top.*


class TransferPaymentRecieptFragment : Fragment() {
    private lateinit var viewModel: PaymentRecieptViewModel
    private lateinit var sessionStorage: SessionStorage
    private lateinit var dialog: AppDialog
    private var userId:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var bundle= bundleOf(
                    KEY_USERID to userId
                )
                findNavController().navigate(R.id.paymentChatHistoryFragment,bundle)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return inflater.inflate(R.layout.fragment_transfer_payment_reciept, container, false)
    }

    companion object {
        fun newInstance() = TransferPaymentRecieptFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var qrCode = requireArguments().getString(KEY_REF_ID)

        dialog= AppDialog(requireContext())
        viewModel = ViewModelProvider(this).get(PaymentRecieptViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())

        viewModel.paymentReceipt(qrCode.toString())
        observer(view)

        iv_back.setOnClickListener {
            var bundle= bundleOf(
                KEY_USERID to userId
            )
            Navigation.findNavController(view).navigate(R.id.paymentChatHistoryFragment,bundle)

        }
    }

    fun observer(view: View) {
        viewModel.apply {
            paymentReceiptStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                    dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        userId=it.data?.beneficiaryId.toString()

                        tv_user_name.text = it.data?.beneficiary?.name
                        tv_reciepient_name.text = it.data?.beneficiary?.name
                        tv_user_contact.text = it.data?.beneficiary?.phoneNumber
                        tv_cash.text = it.data?.amount.toString()+" EmCash"

                        tv_date_time.text= dateFormat(it.data?.createdAt.toString())+" "+ timeformat(it.data?.createdAt.toString())
                        tv_handshake_date.text= dateFormat(it.data?.createdAt.toString())

                        var status = it.data?.status
                        if (status == 1) {
                            tv_progress.text = getString(R.string.payment_success)
                            tv_payment_status2.text=getString(R.string.payment_success)
                            iv_status.setBackgroundResource(R.drawable.ic_payment_success)
                            iv_status_point.setBackgroundResource(R.drawable.ic_green_ellipse)
                        } else if (status == 2) {
                            tv_progress.text = getString(R.string.payment_inprogress)
                            tv_payment_status2.text=getString(R.string.payment_inprogress)
                            iv_status.setBackgroundResource(R.drawable.ic_payment_pending)


                        } else if (status == 3) {
                            tv_progress.text = getString(R.string.payment_failed)
                            tv_payment_status2.text=getString(R.string.payment_failed)
                            iv_status.setBackgroundResource(R.drawable.ic_paymnet_rejected)
                            iv_status_point.setBackgroundResource(R.drawable.ic_red_ellipse)

                        }

                        if(it.data?.handShakingStatus==false){
                            iv_handshake.setBackgroundResource(R.drawable.ic_handshakepending)
                            tv_handshake_date.visibility=View.GONE
                        }else{
                            iv_handshake.setBackgroundResource(R.drawable.handshake)
                            tv_handshake_date.visibility=View.VISIBLE

                        }
                        tv_txn_id.text= trimID(it.data?.walletTransactions?.transactionId.toString())
                        tv_wallet_id.text= trimID(it.data?.walletTransactions?.walletId.toString())
                        tv_description.text=it.data?.description

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