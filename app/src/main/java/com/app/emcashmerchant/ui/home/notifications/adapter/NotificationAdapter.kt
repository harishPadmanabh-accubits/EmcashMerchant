package com.app.emcashmerchant.ui.home.notifications.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.ui.transactionActivity.adapter.CardsAdapter
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapterV2
import com.app.emcashmerchant.utils.extensions.dateFormat
import com.app.emcashmerchant.utils.extensions.timeformat
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlinx.android.synthetic.main.item_transaction_details.view.*

class NotificationAdapter :
    PagingDataAdapter<GroupedNotificationResponse.Data.Row, NotificationAdapter.ViewHolder>(
        DiffUtilCallBack()
    ) {

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationAdapter.ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notifications = getItem(position)

        notifications?.let {
            holder.itemView.apply {

                tv_notification_date.text = it.key
                rv_notification_details.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = NotificationDetailsAdapter(it.notifications)
                }
            }
        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<GroupedNotificationResponse.Data.Row>() {
        override fun areItemsTheSame(
            oldItem: GroupedNotificationResponse.Data.Row,
            newItem: GroupedNotificationResponse.Data.Row
        ): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(
            oldItem: GroupedNotificationResponse.Data.Row,
            newItem: GroupedNotificationResponse.Data.Row
        ): Boolean {
            return oldItem.key == newItem.key


        }

    }

}