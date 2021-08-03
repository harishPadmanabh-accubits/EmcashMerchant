package com.app.emcashmerchant.ui.transaction_history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.data.models.TransactionHistoryGroupViewModel
import kotlinx.android.synthetic.main.item_wallet_activity.view.*

class AllTransactionAdapter(val transactions : List<GroupedTransactionHistoryResponse.Data.Row>) : RecyclerView.Adapter<AllTransactionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllTransactionAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_item, parent, false)
        return AllTransactionAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactions = transactions[position]
        holder.itemView.apply {
            tv_transaction_date.text =transactions.key
            rv_transaction_details.adapter =AllTransactionsDetailsAdapter(transactions.transactions)
        }

    }

    override fun getItemCount(): Int {
        return transactions.size
    }


}