package com.app.emcashmerchant.ui.PaymentChatHistory.adapter

import android.R.attr.button
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.PaymentChatResponse
import com.app.emcashmerchant.utils.extensions.timeformat
import kotlinx.android.synthetic.main.item_chat_details.view.*


class PaymentChatHistoryDetailsAdapter(
    val transactions: List<PaymentChatResponse.Data.Row>
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
        holder.itemView.apply {

            if (transactions[position].isReciever) {
                ///incoming

            } else {

            }
            //outgoing

            tv_time_sent.text = timeformat(transactions[position].createdAt)
            tv_cash_sent.text = transactions[position].amount.toString()
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}