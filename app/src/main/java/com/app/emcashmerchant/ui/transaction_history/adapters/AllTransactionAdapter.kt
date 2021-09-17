package com.app.emcashmerchant.ui.transaction_history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.GroupedTransactionHistoryResponse
import com.app.emcashmerchant.data.models.GroupedWalletTransactionResponse
import com.app.emcashmerchant.data.models.TransactionHistoryGroupViewModel
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapterV2
import kotlinx.android.synthetic.main.item_wallet_activity.view.*

class AllTransactionAdapter :PagingDataAdapter<GroupedTransactionHistoryResponse.Data.Row, AllTransactionAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllTransactionAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_item, parent, false)
        return AllTransactionAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactions = getItem(position)
        transactions?.let {
            holder.itemView.apply {
                tv_transaction_date.text =it.key
                rv_transaction_details.adapter =AllTransactionsDetailsAdapter(it.transactions)
            }

        }

    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<GroupedTransactionHistoryResponse.Data.Row>() {
        override fun areItemsTheSame(
            oldItem: GroupedTransactionHistoryResponse.Data.Row,
            newItem: GroupedTransactionHistoryResponse.Data.Row
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: GroupedTransactionHistoryResponse.Data.Row,
            newItem: GroupedTransactionHistoryResponse.Data.Row
        ): Boolean {
            return false


        }

    }

}