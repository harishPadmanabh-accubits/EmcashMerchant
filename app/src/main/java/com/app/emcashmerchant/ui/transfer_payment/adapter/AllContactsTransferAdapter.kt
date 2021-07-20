package com.app.emcashmerchant.ui.transfer_payment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.AllContactResponse
import com.app.emcashmerchant.utils.*
import kotlinx.android.synthetic.main.contact_item.view.*

class AllContactsTransferAdapter(private val contacts: List<AllContactResponse.Data.Row>, private val view: View) :
    RecyclerView.Adapter<AllContactsTransferAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllContactsTransferAdapter.ViewHolder {
        return AllContactsTransferAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllContactsTransferAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_contact_name.text=contacts?.get(position)?.name
            tv_contact_number.text=contacts?.get(position)?.phoneNumber
            ll_contact.setOnClickListener {
                val bundle = bundleOf(
                    KEY_USERID to contacts.get(position).id
                )

                Navigation.findNavController(view).navigate(R.id.performTransferByContactFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}