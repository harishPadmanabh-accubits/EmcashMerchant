package com.app.emcashmerchant.ui.transactionHistory.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.response.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_FAILED
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_PENDING
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_REJECTED
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_STATUS_SUCCESS
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_REQUEST
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_TOPUP
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_TRANSFER
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_WITHDRAW
import com.app.emcashmerchant.utils.extensions.timeFormat
import kotlinx.android.synthetic.main.item_transaction_details.view.*
import kotlinx.android.synthetic.main.item_transaction_details.view.iv_type_indicator_load_emcash
import kotlinx.android.synthetic.main.item_transaction_details.view.tv_balance
import kotlinx.android.synthetic.main.item_transaction_details.view.tv_time
import kotlinx.android.synthetic.main.item_transaction_details.view.tv_type_indicator
import kotlinx.android.synthetic.main.item_transaction_details.view.tv_value_changed


class AllTransactionsDetailsAdapter(
    val transactions: List<GroupedTransactionHistoryResponse.Data.Row.Transaction>) :
    RecyclerView.Adapter<AllTransactionsDetailsAdapter.ViewHolder>() {

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllTransactionsDetailsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_details, parent, false)
        return AllTransactionsDetailsAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: AllTransactionsDetailsAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_time.text = timeFormat(transactions[position].updatedAt)
            val type = transactions[position].type
            val status = transactions[position].status



            if (type == TRANSACTION_TYPE_TRANSFER) {
                tv_type_indicator.text = transactions[position].transferUserInfo.name

                if (status == TRANSACTION_STATUS_SUCCESS) {//success
                    tv_balance.text =
                        transactions[position].walletTransactionInfo.balance.toString()

                    if (transactions[position].walletTransactionInfo.mode == 1) {
                        tv_value_changed.text =
                            "+" + transactions[position].amount.toString() + " EmCash"

                        iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_inbound)

                        tv_balance.apply {
                            setTextColor(ContextCompat.getColor(context, R.color.green))
                            text =
                                transactions.get(position).walletTransactionInfo.balance.toString()
                        }
                    } else {
                        tv_value_changed.text =
                            "-" + transactions[position].amount.toString() + " EmCash"

                        iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_outbound)
                        tv_balance.apply {
                            setTextColor(ContextCompat.getColor(context, R.color.red))
                            text =
                                transactions.get(position).walletTransactionInfo.balance.toString()
                        }
                    }
                } else if (status == TRANSACTION_STATUS_PENDING) {//pending
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_pending)

                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    //need to add pending icon
                    tv_inf_balance.text = "Transfer Pending"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_FAILED) {//failed

                    //need to add failed icon
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_failed)
                    tv_inf_balance.text = "Failed"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_REJECTED) {//rejected

                    //need to add reject icon
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_rejected)
                    tv_inf_balance.text = "Rejected"
                    tv_balance.visibility = View.GONE

                }


            } else if (type == TRANSACTION_TYPE_TOPUP) {//emcash loaded cases
                if (status == TRANSACTION_STATUS_SUCCESS) {
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_emcash_loaded)
                    tv_balance.text =
                        transactions[position].walletTransactionInfo.balance.toString()
                    tv_type_indicator.text = "Emcash Loaded"
                    tv_value_changed.text =
                        "+" + transactions[position].amount.toString() + " EmCash"

                    tv_balance.apply {

                        setTextColor(ContextCompat.getColor(context, R.color.green))
                        text = transactions.get(position).walletTransactionInfo.balance.toString()
                    }

                } else if (status == TRANSACTION_STATUS_PENDING) {
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_pending)

                    tv_type_indicator.text = "Emcash Load"
                    tv_inf_balance.text = "Pending"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_FAILED) {
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_failed)
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    tv_type_indicator.text = "Emcash Load"
                    tv_inf_balance.text = "Failed"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_REJECTED) {//rejected

                    //need to add reject icon
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_rejected)
                    tv_inf_balance.text = "Rejected"
                    tv_balance.visibility = View.GONE

                }


            } else if (type == TRANSACTION_TYPE_WITHDRAW) {//emcash converted cases
                if (status == TRANSACTION_STATUS_SUCCESS) {
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_emcash_converted)
                    tv_value_changed.text =
                        "-" + transactions[position].amount.toString() + " EmCash"
                    tv_balance.text =
                        transactions[position].walletTransactionInfo.balance.toString()
                    tv_type_indicator.text = "Emcash Converted"
                    tv_balance.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                        text = transactions.get(position).walletTransactionInfo.balance.toString()
                    }

                } else if (status == TRANSACTION_STATUS_PENDING) {
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_pending)
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    tv_type_indicator.text = "Emcash Conversion"
                    tv_inf_balance.text = "Pending"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_FAILED) {
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_failed)
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    tv_type_indicator.text = "Emcash Conversion"
                    tv_inf_balance.text = "Failed"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_REJECTED) {//rejected

                    //need to add reject icon
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_rejected)
                    tv_inf_balance.text = "Rejected"
                    tv_balance.visibility = View.GONE

                }


            } else if (type == TRANSACTION_TYPE_REQUEST) {//request
//                iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_pending)
//                tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
//                tv_type_indicator.text = transactions.get(position).transferUserInfo.name
//                tv_inf_balance.text = "Request Pending"
//                tv_balance.visibility = View.GONE

                tv_type_indicator.text = transactions[position].transferUserInfo.name

                if (status == TRANSACTION_STATUS_SUCCESS) {//success
                    tv_balance.text =
                        transactions[position].walletTransactionInfo.balance.toString()

                    if (transactions[position].walletTransactionInfo.mode == 1) {
                        tv_value_changed.text =
                            "+" + transactions[position].amount.toString() + " EmCash"

                        iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_inbound)

                        tv_balance.apply {
                            setTextColor(ContextCompat.getColor(context, R.color.green))
                            text =
                                transactions.get(position).walletTransactionInfo.balance.toString()
                        }
                    } else {
                        tv_value_changed.text =
                            "-" + transactions[position].amount.toString() + " EmCash"

                        iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_outbound)
                        tv_balance.apply {
                            setTextColor(ContextCompat.getColor(context, R.color.red))
                            text =
                                transactions.get(position).walletTransactionInfo.balance.toString()
                        }
                    }
                } else if (status == TRANSACTION_STATUS_PENDING) {//pending
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_pending)

                    tv_value_changed.text =
                        transactions[position].amount.toString() + " EmCash"
                    //need to add pending icon
                    tv_inf_balance.text = "Request Pending"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_FAILED) {//failed

                    //need to add failed icon
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_failed)
                    tv_inf_balance.text = "Failed"
                    tv_balance.visibility = View.GONE

                } else if (status == TRANSACTION_STATUS_REJECTED) {//rejected

                    //need to add reject icon
                    tv_value_changed.text = transactions[position].amount.toString() + " EmCash"
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_rejected)
                    tv_inf_balance.text = "Rejected"
                    tv_balance.visibility = View.GONE

                }


            }


        }


    }

    override fun getItemCount(): Int {
        return transactions.size
    }


}