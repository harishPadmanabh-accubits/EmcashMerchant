package com.app.emcashmerchant.ui.paymentRequest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.response.GroupedContactsResponse
import kotlinx.android.synthetic.main.contact_item.view.*
import timber.log.Timber


class AllContactsRequestsAdapter(
    private val clickListener: ContactsItemClickListener
) :
    PagingDataAdapter<GroupedContactsResponse.Data.Row, AllContactsRequestsAdapter.ViewHolder>(
        DiffUtilCallBack()
    ) {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllContactsRequestsAdapter.ViewHolder {
        return AllContactsRequestsAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllContactsRequestsAdapter.ViewHolder, position: Int) {
        var contacts = getItem(position)

        holder.itemView.apply {

            contacts?.let {
                tv_firstLetterName.text = it.key
                it.contacts?.let {
                    rv_contacts.adapter =
                        AllRequestContactsDetailsAdapter(it, clickListener)
                    Timber.e("ContactsData ${it.toString()}")

                }


            }
        }

    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<GroupedContactsResponse.Data.Row>() {
        override fun areItemsTheSame(
            oldItem: GroupedContactsResponse.Data.Row,
            newItem: GroupedContactsResponse.Data.Row
        ): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(
            oldItem: GroupedContactsResponse.Data.Row,
            newItem: GroupedContactsResponse.Data.Row
        ): Boolean {
            return false


        }

    }


}

interface ContactsItemClickListener {
    fun onContactClicked(contact: GroupedContactsResponse.Data.Row.Contact)
}