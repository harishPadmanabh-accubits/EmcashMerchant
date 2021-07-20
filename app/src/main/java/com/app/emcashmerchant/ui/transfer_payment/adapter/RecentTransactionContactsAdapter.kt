package com.app.emcashmerchant.ui.transfer_payment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.utils.KEY_USERID
import kotlinx.android.synthetic.main.item_recent_payment_contacts.view.*

class RecentTransactionContactsAdapter(
    private val transaction: List<RecentTransactionResponse.Data.Row>,
    private val view: View
) : RecyclerView.Adapter<RecentTransactionContactsAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recent_payment_contacts, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            lpi_imageView.setProfileName(transaction[position].name)
            ll_contact.setOnClickListener {
                val bundle = bundleOf(
                    KEY_USERID to transaction.get(position).userId.toString()
                )

                Navigation.findNavController(view).navigate(R.id.performTransferByContactFragment, bundle)
            }
        }

    }

    override fun getItemCount(): Int {
        return transaction.size
    }


}