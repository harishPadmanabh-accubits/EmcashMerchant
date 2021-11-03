package com.app.emcashmerchant.ui.paymentChatHistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.GroupedChatHistoryResponse
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_MODE_DEBIT
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_FAILED
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_PENDING
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_REJECTED
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_SUCCESS
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_REQUEST
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_TRANSFER
import com.app.emcashmerchant.utils.extensions.timeFormat
import kotlinx.android.synthetic.main.item_chat_details.view.*


class PaymentChatHistoryDetailsAdapter(
    val transactions: List<GroupedChatHistoryResponse.Data.Row.Transaction>,
    private val  ClickListener: ChatItemClickListener

) : RecyclerView.Adapter<PaymentChatHistoryDetailsAdapter.ViewHolder>() {

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentChatHistoryDetailsAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_details, parent, false)
        return PaymentChatHistoryDetailsAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: PaymentChatHistoryDetailsAdapter.ViewHolder,
        position: Int
    ) {

        var status = transactions[position].status
        var type = transactions[position].type
        var mode = transactions[position].mode

        holder.itemView.apply {

            ll_chat_receive.setOnClickListener {
                var bundle = bundleOf(
                    KEY_REF_ID to transactions[position].transactionId
                )

                findNavController().navigate(R.id.transferpaymentRecieptFragment, bundle)
            }

            ll_chat_send.setOnClickListener {

                var bundle = bundleOf(
                    KEY_REF_ID to transactions[position].transactionId
                )

                findNavController().navigate(R.id.transferpaymentRecieptFragment, bundle)
            }
            bt_reject.setOnClickListener {
                ClickListener.onChatRejectClicked(transactions[position])
            }
            bt_accept.setOnClickListener {
                ClickListener.onChatAcceptClicked(transactions[position])

            }
            if (transactions[position].isReciever)
            {
                ll_chat_receive.visibility = View.VISIBLE
                ll_chat_send.visibility = View.GONE

                if (transactions[position].isConfirm) {
                    ll_button_holder.visibility = View.VISIBLE
                } else {
                    ll_button_holder.visibility = View.GONE
                }

                tv_time_receive.text = timeFormat(transactions[position].updatedAt)
                tv_cash_receive.text = transactions[position].amount.toString()


                if (status == TRANSACTION_STATUS_SUCCESS) {
                    if (mode == TRANSACTION_MODE_DEBIT) {
                        tv_payment_type_label_receive.text = "Payment Received"

                    } else {
                        tv_payment_type_label_receive.text = "Payment Sent"

                    }
                    iv_image_receive_status.setBackgroundResource(R.drawable.ic_success)

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_receive.text = "Transfer Success"
                    } else if (type == TRANSACTION_TYPE_REQUEST) {
                        tv_status_receive.text = "Request Success"

                    }
                }
                else if (status == TRANSACTION_STATUS_PENDING) {
                    iv_image_receive_status.setBackgroundResource(R.drawable.ic_pending)

                    tv_payment_type_label_receive.text = "Payment Pending"

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_receive.text = "Transfer Pending"


                    } else if (type == TRANSACTION_TYPE_REQUEST) {
                        tv_status_receive.text = "Request Pending"


                    }
                }
                else if (status == TRANSACTION_STATUS_FAILED) {
                    iv_image_receive_status.setBackgroundResource(R.drawable.ic_failed)

                    tv_payment_type_label_receive.text = "Payment Failed"

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_receive.text = "Transfer Failed"


                    } else if (type == TRANSACTION_TYPE_REQUEST) {
                        tv_status_receive.text = "Request Failed"

                    }
                }
                else if (status == TRANSACTION_STATUS_REJECTED) {
                    iv_image_receive_status.setBackgroundResource(R.drawable.ic_rejected)

                    tv_payment_type_label_receive.text = "Payment Rejected"

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_receive.text = "Transfer Rejected"


                    } else if (type == TRANSACTION_TYPE_REQUEST) {
                        tv_status_receive.text = "Request Rejected"

                    }
                }


            }
            else {

                ll_chat_receive.visibility = View.GONE
                ll_chat_send.visibility = View.VISIBLE

                tv_time_send.text = timeFormat(transactions[position].updatedAt)
                tv_cash_send.text = transactions[position].amount.toString()


                if (status == TRANSACTION_STATUS_SUCCESS) {

                    if (mode == 2) {
                        tv_payment_type_label_send.text = "Payment Received"

                    } else {
                        tv_payment_type_label_send.text = "Payment Sent"

                    }
                    iv_image_send_status.setBackgroundResource(R.drawable.ic_chat_success)

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_send.text = "Transfer Success"
                    } else if (type == TRANSACTION_TYPE_REQUEST) {
                        tv_status_send.text = "Request Success"

                    }
                } else if (status == TRANSACTION_STATUS_PENDING) {
                    iv_image_send_status.setBackgroundResource(R.drawable.ic_chat_pending)
                    tv_payment_type_label_send.text = "Payment Intiated"

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_send.text = "Transfer Pending"

                    } else if (type == TRANSACTION_TYPE_REQUEST) {
                        tv_status_send.text = "Request Pending"

                    }
                } else if (status == TRANSACTION_STATUS_FAILED) {
                    iv_image_send_status.setBackgroundResource(R.drawable.ic_failed)
                    tv_payment_type_label_send.text = "Payment Failed"

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_send.text = "Transfer Failed"

                    } else if (type == TRANSACTION_TYPE_REQUEST) {
                        tv_status_send.text = "Request Failed"

                    }
                } else if (status == TRANSACTION_STATUS_REJECTED) {
                    iv_image_send_status.setBackgroundResource(R.drawable.ic_rejected)
                    tv_payment_type_label_send.text = "Payment Rejected"

                    if (type == TRANSACTION_TYPE_TRANSFER) {
                        tv_status_send.text = "Transfer Rejected"


                    } else if (type == TRANSACTION_TYPE_REQUEST) {//4
                        tv_status_send.text = "Request Rejected"

                    }
                }

            }


        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}

interface ChatItemClickListener {
    fun onChatRejectClicked(payment: GroupedChatHistoryResponse.Data.Row.Transaction)
    fun onChatAcceptClicked(payment: GroupedChatHistoryResponse.Data.Row.Transaction)


}

