package com.app.emcashmerchant.ui.payment_request.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.AllContactResponse
import kotlinx.android.synthetic.main.contact_item.view.*

class AllContactsRequestAdapter(
    private val contacts: List<AllContactResponse.Data.Row>,
   private val clickListener: ContactsItemClickListener
) : RecyclerView.Adapter<AllContactsRequestAdapter.ViewHolder>() {

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return AllContactsRequestAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int

    ) {
        holder.itemView.apply {
            tv_contact_name.text = contacts?.get(position)?.name
            tv_contact_number.text = contacts?.get(position)?.phoneNumber


            ll_contact.setOnClickListener {
                clickListener.onContactClicked(contacts[position])
            }
        }


    }

    override fun getItemCount(): Int {
        return contacts.size

    }


}

interface ContactsItemClickListener {
    fun onContactClicked(contact: AllContactResponse.Data.Row)
}