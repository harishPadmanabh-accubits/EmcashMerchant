package com.app.emcashmerchant.ui.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.response.GroupedWalletTransactionResponse
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_MODE_CREDIT
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_REQUEST
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_TOPUP
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_TRANSFER
import com.app.emcashmerchant.utils.TransactionUtils.Companion.TRANSACTION_TYPE_WITHDRAW
import com.app.emcashmerchant.utils.extensions.timeFormat
import kotlinx.android.synthetic.main.item_inner_activity_details.view.*

class WalletTransactionDetailsAdapterV2(
    val transactions : List<GroupedWalletTransactionResponse.Data.Row.Transaction>):RecyclerView.Adapter<WalletTransactionDetailsAdapterV2.ViewHolder>() {
    open class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inner_activity_details, parent, false)
    )

    override fun getItemCount() =transactions.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTransaction =transactions[position]
        holder.itemView.apply {
            tv_time.text= timeFormat(transactions[position].updatedAt)
            val type=transactions[position].transactionInfo.type

            if(type==TRANSACTION_TYPE_TRANSFER){ // Show corresponding icon in image view
                if (transactions[position].mode == TRANSACTION_MODE_CREDIT) {
                    tv_type_indicator.text=transactions[position].remitter.name
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_inbound)

                } else {
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_outbound)
                    tv_type_indicator.text=transactions[position].beneficiary.name

                }
            }

            else if(type==TRANSACTION_TYPE_TOPUP){
                iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_emcash_loaded)
                tv_type_indicator.text="Emcash Loaded"
            }

            else if(type==TRANSACTION_TYPE_WITHDRAW){
                iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_emcash_converted)
                tv_type_indicator.text="Emcash Converted"

            }

            else if(type ==TRANSACTION_TYPE_REQUEST){
                if (transactions[position].mode == TRANSACTION_MODE_CREDIT) {
                    tv_type_indicator.text=transactions[position].remitter.name
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_inbound)

                } else {
                    iv_type_indicator_load_emcash.setBackgroundResource(R.drawable.ic_outbound)
                    tv_type_indicator.text=transactions[position].beneficiary.name

                }
            }



            if (transactions.get(position).mode == TRANSACTION_MODE_CREDIT) {
                tv_value_changed.text="+"+transactions.get(position).transactionInfo.amount.toString()+" EmCash"

            } else {
                tv_value_changed.text="-"+transactions.get(position).transactionInfo.amount.toString()+" EmCash"

            }

            tv_balance.apply {
                tv_balance.text = transactions.get(position).balance.toString()
                if (transactions[position].mode == TRANSACTION_MODE_CREDIT) {
                    setTextColor(ContextCompat.getColor(context,R.color.green))

                } else {

                    setTextColor(ContextCompat.getColor(context,R.color.red))

                }
            }

        }
    }
}