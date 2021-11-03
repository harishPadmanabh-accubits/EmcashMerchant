package com.app.emcashmerchant.ui.transferPayment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.GroupedContactsResponse
import kotlinx.android.synthetic.main.contact_item.view.*
import timber.log.Timber


class AllContactsTransferAdapter :
    PagingDataAdapter<GroupedContactsResponse.Data.Row,AllContactsTransferAdapter.ViewHolder>(DiffUtilCallBack()) {
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
        var contacts=getItem(position)

        holder.itemView.apply {

            contacts?.let {
                tv_firstLetterName.text=it.key
                it.contacts?.let {
                    rv_contacts.adapter = AllContactsDetailsAdapter(it)
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
            return false
        }

        override fun areContentsTheSame(
            oldItem: GroupedContactsResponse.Data.Row,
            newItem: GroupedContactsResponse.Data.Row
        ): Boolean {
            return false


        }

    }

}