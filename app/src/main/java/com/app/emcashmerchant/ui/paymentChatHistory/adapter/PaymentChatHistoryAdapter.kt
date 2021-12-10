package com.app.emcashmerchant.ui.paymentChatHistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.response.GroupedChatHistoryResponse
import com.app.emcashmerchant.utils.extensions.getCurrentDate
import kotlinx.android.synthetic.main.payment_transaction_chat_item.view.*

class PaymentChatHistoryAdapter(
    private val clickListener: ChatItemClickListener
) : PagingDataAdapter<GroupedChatHistoryResponse.Data.Row, PaymentChatHistoryAdapter.ViewHolder>(
    DiffUtilCallBack()
) {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_transaction_chat_item, parent, false)
        return PaymentChatHistoryAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            val transactions = getItem(position)

            transactions?.let {
                if (getCurrentDate().equals(it.key)) {
                    tv_date.text = "Today"
                } else {
                    tv_date.text = it.key

                }

                rv_chat_details.apply {

                    adapter = PaymentChatHistoryDetailsAdapter(
                        it.transactions, clickListener

                    )


                }
            }

        }
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<GroupedChatHistoryResponse.Data.Row>() {
        override fun areItemsTheSame(
            oldItem: GroupedChatHistoryResponse.Data.Row,
            newItem: GroupedChatHistoryResponse.Data.Row
        ): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(
            oldItem: GroupedChatHistoryResponse.Data.Row,
            newItem: GroupedChatHistoryResponse.Data.Row
        ): Boolean {
            return oldItem == newItem
        }

    }


}