package com.app.emcashmerchant.ui.transaction_history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.DummyTransactionDetalsModel
import com.app.emcashmerchant.data.models.TRANSACTED_INBOUND
import com.app.emcashmerchant.data.models.TRANSACTED_OUTBOUND
import kotlinx.android.synthetic.main.item_inner_activity_details.view.*

class TransactionDetailsAdapter(val transactionList: List<DummyTransactionDetalsModel>) :
    RecyclerView.Adapter<TransactionDetailsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inner_activity_details, parent, false)
    )

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = transactionList[position]
        holder.itemView.apply {
            if (currentItem.type == TRANSACTED_INBOUND) {

                iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_payment_recieved)

                tv_type_indicator.text = context.getString(R.string.payment_recieved)
                tv_time.text = currentItem.time
                tv_value_changed.text = currentItem.valueLoaded.plus(" EmCash")
                tv_balance.text = currentItem.Balance
            } else if (currentItem.type == TRANSACTED_OUTBOUND) {

                iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_icon_convert)

                tv_type_indicator.text = context.getString(R.string.payment_converted)
                tv_time.text = currentItem.time
                tv_value_changed.text = currentItem.valueLoaded.plus(" EmCash")
                tv_balance.text = currentItem.Balance
            }
        }
    }


}