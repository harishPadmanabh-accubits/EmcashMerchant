package com.app.emcashmerchant.ui.PaymentChatHistory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.PaymentChatResponse
import com.app.emcashmerchant.utils.extensions.getCurrentDate
import kotlinx.android.synthetic.main.payment_transaction_chat_item.view.*

class PaymentChatHistoryAdapter(
    val transactions: MutableList<PaymentChatResponse.ChatTransactionViewModel>,
    private val clickListener: ChatItemClickListener
) :
    RecyclerView.Adapter<PaymentChatHistoryAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_transaction_chat_item, parent, false)
        return PaymentChatHistoryAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {


            if (getCurrentDate().equals(transactions[position].date)) {
                tv_date.text = "Today"
            } else {
                tv_date.text = transactions[position].date

            }
            rv_chat_details.apply {
                adapter = PaymentChatHistoryDetailsAdapter(
                    transactions[position].activities.asReversed(),clickListener)
            }

        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

}