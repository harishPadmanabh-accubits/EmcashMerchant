package com.app.emcashmerchant.ui.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.GroupedWalletTransactionResponse
import com.app.emcashmerchant.utils.extensions.dateFormat
import kotlinx.android.synthetic.main.item_wallet_activity.view.*
import org.w3c.dom.CharacterData
import timber.log.Timber

class WalletTransactionAdapterV2() :
    PagingDataAdapter<GroupedWalletTransactionResponse.Data.Row, WalletTransactionAdapterV2.ViewHolder>(
        DiffUtilCallBack()
    ) {

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_wallet_activity, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentActivity = getItem(position)
        holder.itemView.apply {
            currentActivity?.let {
                tv_transaction_date.text = it.key
                Timber.e("transactiondata${it.transactions}")
                rv_transaction_details.adapter =
                    WalletTransactionDetailsAdapterV2(it.transactions)
            }

        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<GroupedWalletTransactionResponse.Data.Row>() {
        override fun areItemsTheSame(
            oldItem: GroupedWalletTransactionResponse.Data.Row,
            newItem: GroupedWalletTransactionResponse.Data.Row
        ): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(
            oldItem: GroupedWalletTransactionResponse.Data.Row,
            newItem: GroupedWalletTransactionResponse.Data.Row
        ): Boolean {
            return oldItem.key == newItem.key


        }

    }
}