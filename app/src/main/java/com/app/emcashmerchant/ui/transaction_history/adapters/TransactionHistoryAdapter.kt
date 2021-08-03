package com.app.emcashmerchant.ui.transaction_history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.DummyTransactionModel
import com.app.emcashmerchant.data.models.TRANSACTED_INBOUND
import com.app.emcashmerchant.data.models.TRANSACTED_OUTBOUND
import kotlinx.android.synthetic.main.item_wallet_activity.view.*

class TransactionHistoryAdapter(val transactionHistory: List<DummyTransactionModel>) :
    RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var transactionType = TransactionFilter.ALL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_wallet_activity, parent, false)

    )

    override fun getItemCount(): Int = transactionHistory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = transactionHistory[position]
        holder.itemView.apply {
            when (transactionType) {
                TransactionFilter.ALL -> {
                    tv_transaction_date.text = currentItem.date
                    rv_transaction_details.apply {
                        adapter =
                            TransactionDetailsAdapter(
                                currentItem.transactionList
                            )
                    }
                }
                TransactionFilter.INBOUND -> {
                    tv_transaction_date.text = currentItem.date
                    rv_transaction_details.apply {
                        adapter =
                            TransactionDetailsAdapter(
                                currentItem.transactionList.filter {
                                    it.type == TRANSACTED_INBOUND
                                }
                            )
                    }
                }
                TransactionFilter.OUTBOUND -> {
                    tv_transaction_date.text = currentItem.date
                    rv_transaction_details.apply {
                        adapter =
                            TransactionDetailsAdapter(
                                currentItem.transactionList.filter {
                                    it.type == TRANSACTED_OUTBOUND
                                }
                            )
                    }
                }
            }


        }
    }
}

enum class TransactionFilter {
    ALL, INBOUND, OUTBOUND
}