package com.app.emcashmerchant.ui.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.WalletActivityModel
import kotlinx.android.synthetic.main.item_wallet_activity.view.*

class WalletTransactionAdapterV2(
    val activities : ArrayList<WalletActivityModel>
) :RecyclerView.Adapter<WalletTransactionAdapterV2.ViewHolder>(){
    open class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_wallet_activity, parent, false)
    )

    override fun getItemCount() =activities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentActivity = activities[position]
        holder.itemView.apply {
            tv_transaction_date.text =currentActivity.date
            rv_transaction_details.adapter =WalletTransactionDetailsAdapterV2(currentActivity.activities)
        }
    }
}