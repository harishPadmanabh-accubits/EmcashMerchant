package com.app.emcashmerchant.ui.payment_request.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.AllContactResponse
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
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
        var userLevel=contacts?.get(position)?.ppp
        var roleId=contacts?.get(position)?.roleId
        var profileImage=contacts?.get(position)?.profileImage


        holder.itemView.apply {
            if (profileImage != null) {
                iv_user_image.loadImageWithUrl(profileImage.toString())
                iv_user_image.visibility = View.VISIBLE
                tv_firstLetterr.visibility = View.INVISIBLE


            } else {
                tv_firstLetterr.text = contacts?.get(position)?.name.toString()[0].toString()
                iv_user_image.visibility = View.INVISIBLE
                tv_firstLetterr.visibility = View.VISIBLE
                fl_user_level.setBackgroundResource(R.drawable.black_round)

            }
            
            if (roleId == 3) {
                if (userLevel == 1) {
                    fl_user_level.setBackgroundResource(R.drawable.green_round)
                } else if (userLevel == 2) {
                    fl_user_level.setBackgroundResource((R.drawable.yellow_round))
                } else if (userLevel == 4) {
                    fl_user_level.setBackgroundResource(R.drawable.red_round)

                }
            }

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