package com.app.emcashmerchant.ui.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.ui.transactionActivity.adapter.CardsAdapter
import com.app.emcashmerchant.utils.extensions.dateFormat
import kotlinx.android.synthetic.main.item_wallet_activity.view.*

class WalletTransactionAdapter(val transactions: ArrayList<WalletTransactionResponse.Data.Row>): RecyclerView.Adapter<WalletTransactionAdapter.ViewHolder>() {

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WalletTransactionAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet_activity, parent, false)
        return WalletTransactionAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return transactions.size
    }

    override fun onBindViewHolder(holder: WalletTransactionAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_transaction_date.text=dateFormat(transactions[position].updatedAt).toString()

            rv_transaction_details.apply {
                adapter = WalletTransactionDetailsAdapter(arrayListOf(transactions.get(position)))
            }
        }
    }
}