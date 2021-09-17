package com.app.emcashmerchant.ui.paymentReciept

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.*
import kotlinx.android.synthetic.main.fragment_transfer_payment_reciept.*
import kotlinx.android.synthetic.main.fragment_transfer_payment_reciept.iv_back
import kotlinx.android.synthetic.main.item_notification_details.view.*
import kotlinx.android.synthetic.main.layout_payment_receipt_bottom.*
import kotlinx.android.synthetic.main.layout_payment_reciept_middle.*
import kotlinx.android.synthetic.main.layout_payment_reciept_top.*
import kotlinx.android.synthetic.main.layout_payment_reciept_top.fl_user_level
import kotlinx.android.synthetic.main.layout_payment_reciept_top.tv_firstLetterr


class TransferPaymentRecieptFragment : Fragment() {
    private lateinit var viewModel: PaymentRecieptViewModel
    private lateinit var sessionStorage: SessionStorage
    private lateinit var dialog: AppDialog
    private var userId: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_transfer_payment_reciept, container, false)
    }

    companion object {
        fun newInstance() = TransferPaymentRecieptFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var qrCode = requireArguments().getString(KEY_REF_ID)

        dialog = AppDialog(requireContext())
        viewModel = ViewModelProvider(this).get(PaymentRecieptViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())

        viewModel.paymentReceipt(qrCode.toString())
        observer()

        iv_back.setOnClickListener {
            var bundle = bundleOf(
                KEY_USERID to userId
            )
            Navigation.findNavController(view).navigate(R.id.paymentChatHistoryFragment, bundle)

        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var bundle = bundleOf(
                    KEY_USERID to userId
                )
                findNavController().navigate(R.id.paymentChatHistoryFragment, bundle)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

    }

    fun observer() {
        viewModel.apply {
            paymentReceiptStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        cl_main.visibility=View.VISIBLE
                        iv_user_dp.loadImageWithUrlUser(it.data?.remitter?.profileImage)
                        userId = it.data?.beneficiary?.userId.toString()

                        if (it.data?.beneficiary?.profileImage != null) {
                            iv_user_Image.visibility = View.VISIBLE
                            iv_receipient_dp.visibility = View.VISIBLE
                            tv_firstLetter_recipient.visibility=View.GONE
                            tv_firstLetterr.visibility = View.INVISIBLE
                            iv_user_Image.loadImageWithUrl(it.data?.beneficiary?.profileImage.toString())
                            iv_receipient_dp.loadImageWithUrl(it.data?.beneficiary?.profileImage.toString())

                        } else {
                            iv_receipient_dp.visibility = View.INVISIBLE
                            iv_user_Image.visibility = View.INVISIBLE
                            tv_firstLetterr.visibility = View.VISIBLE
                            tv_firstLetter_recipient.visibility=View.VISIBLE
                            tv_firstLetter_recipient.text =
                                it.data?.beneficiary?.name.toString()[0].toString()
                            tv_firstLetterr.text = it.data?.beneficiary?.name.toString()[0].toString()
                            tv_firstLetterr.setTextColor(resources.getColor(R.color.white))
                            fl_user_level.setBackgroundResource(R.drawable.greyfilled_round)

                        }

                        tv_user_name.text = it.data?.beneficiary?.name
                        tv_reciepient_name.text = it.data?.beneficiary?.name
                        tv_user_contact.text = it.data?.beneficiary?.phoneNumber
                        tv_cash.text = it.data?.amount.toString() + " EmCash"

                        tv_date_time.text =
                            dateFormat(it.data?.createdAt.toString()) + " " + timeformat(it.data?.createdAt.toString())
                        tv_handshake_date.text = dateFormat(it.data?.createdAt.toString())

                        var status = it.data?.status
                        var type = it.data?.type

                        var role = it.data?.beneficiary?.roleId
                        var userLevel = it.data?.beneficiary?.ppp

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

                        if (status == 1) {
                            tv_progress.text = getString(R.string.payment_success)
                            tv_payment_status2.text = getString(R.string.payment_success)

                            if (type == 1) {
                                tv_request_status.text = "Transfer Success"

                            } else if (type == 4) {
                                tv_request_status.text = "Request Success"

                            }

                            iv_status.setBackgroundResource(R.drawable.ic_payment_success)
                            iv_status_point.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));

                        } else if (status == 2) {
                            tv_progress.text = getString(R.string.payment_inprogress)
                            tv_payment_status2.text = getString(R.string.payment_inprogress)
                            tv_request_status.text = getString(R.string.payment_inprogress)

                            iv_status.setBackgroundResource(R.drawable.ic_payment_pending)
                            iv_status_point.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange));

                            if (type == 1) {
                                tv_request_status.text = "Transfer In Progress"

                            } else if (type == 4) {
                                tv_request_status.text = "Request In Progress"

                            }


                        } else if (status == 3) {
                            tv_progress.text = getString(R.string.payment_failed)
                            tv_payment_status2.text = getString(R.string.payment_failed)
                            tv_request_status.text = getString(R.string.payment_failed)

                            if (type == 1) {
                                tv_request_status.text = "Transfer Failed"

                            } else if (type == 4) {
                                tv_request_status.text = "Request Failed"

                            }
                            iv_status.setBackgroundResource(R.drawable.ic_paymnet_failed)
                            iv_status_point.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red));


                        } else if (status == 4) {
                            tv_progress.text = getString(R.string.payment_rejected)
                            tv_payment_status2.text = getString(R.string.payment_rejected)
                            tv_request_status.text = getString(R.string.payment_rejected)

                            if (type == 1) {
                                tv_request_status.text = "Transfer Rejected"

                            } else if (type == 4) {
                                tv_request_status.text = "Request Rejected"

                            }

                            iv_status.setBackgroundResource(R.drawable.ic_payment_rejected)
                            iv_status_point.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red));

                        }

                        if (it.data?.handShakingStatus == false) {
                            if (status == 4) {
                                iv_handshake.setBackgroundResource(R.drawable.ic_handshake_rejected)
                                tv_handshake_date.visibility = View.GONE
                                tv_handshake_status.visibility = View.INVISIBLE
                            } else if (status == 2) {
                                iv_handshake.setBackgroundResource(R.drawable.ic_handshakepending)
                                tv_handshake_date.visibility = View.GONE
                                tv_handshake_status.visibility = View.INVISIBLE
                            }

                        } else {
                            iv_handshake.setBackgroundResource(R.drawable.handshake)
                            tv_handshake_date.visibility = View.VISIBLE
                            tv_handshake_status.visibility = View.VISIBLE

                        }
                        tv_txn_id.text = trimID(it.data?.id.toString())
                        tv_wallet_id.text = trimID(it.data?.walletTransactions?.walletId.toString())
                        tv_description.text = it.data?.description

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