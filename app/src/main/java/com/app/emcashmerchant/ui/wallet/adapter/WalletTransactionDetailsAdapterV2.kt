package com.app.emcashmerchant.ui.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.utils.extensions.timeformat
import kotlinx.android.synthetic.main.item_inner_activity_details.view.*

class WalletTransactionDetailsAdapterV2(
    val transactions : List<WalletTransactionResponse.Data.Row>
) :RecyclerView.Adapter<WalletTransactionDetailsAdapterV2.ViewHolder>(){
    open class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inner_activity_details, parent, false)
    )

    override fun getItemCount() =transactions.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTransaction =transactions[position]
        holder.itemView.apply {
            tv_time.text= timeformat(transactions[position].updatedAt)
            var type=transactions[position].transactionInfo.type

            if(type==1){
                if (transactions[position].mode == 1) {
                    tv_type_indicator.text=transactions[position].transactionInfo.remitter.name
                } else {
                    tv_type_indicator.text=transactions[position].transactionInfo.beneficiary.name

                }
            }
            else if(type==2){
                iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_payment_recieved)
                tv_type_indicator.text="Payment recieved"
            }else if(type==3){
                iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_icon_convert)
                tv_type_indicator.text="Emcash Converted"

            }
            if (transactions.get(position).mode == 1) {
                tv_value_changed.text="+"+transactions.get(position).transactionInfo.amount.toString()+" EmCash"

            } else {
                tv_value_changed.text="-"+transactions.get(position).transactionInfo.amount.toString()+" EmCash"

            }
            tv_balance.apply {
                tv_balance.text = transactions.get(position).balance.toString()
                if (transactions[position].mode == 1) {
                    setTextColor(ContextCompat.getColor(context,R.color.green))

                } else {
                    setTextColor(ContextCompat.getColor(context,R.color.red))

                }
            }

        }
    }
}