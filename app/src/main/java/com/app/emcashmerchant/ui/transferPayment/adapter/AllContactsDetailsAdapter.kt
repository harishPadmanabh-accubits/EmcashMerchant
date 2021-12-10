package com.app.emcashmerchant.ui.transferPayment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.response.GroupedContactsResponse
import com.app.emcashmerchant.utils.KEY_LEVEL_COLOUR
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import kotlinx.android.synthetic.main.contact_item_list.view.*
import kotlinx.android.synthetic.main.contact_item_list.view.fl_user_level
import kotlinx.android.synthetic.main.contact_item_list.view.iv_user_image
import kotlinx.android.synthetic.main.contact_item_list.view.ll_tvHolder


class AllContactsDetailsAdapter(val contacts: List<GroupedContactsResponse.Data.Row.Contact>) :
    RecyclerView.Adapter<AllContactsDetailsAdapter.ViewHolder>() {

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllContactsDetailsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item_list, parent, false)
        return AllContactsDetailsAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: AllContactsDetailsAdapter.ViewHolder, position: Int) {

        var userLevel = contacts?.get(position)?.ppp
        var roleId = contacts?.get(position)?.roleId
        var profileImage = contacts?.get(position)?.profileImage

        holder.itemView.apply {


            if (profileImage != null) {
                iv_user_image.loadImageWithUrl(profileImage.toString())
                iv_user_image.visibility = View.VISIBLE
                tv_firstLetterr.visibility = View.INVISIBLE
                ll_tvHolder.visibility=View.INVISIBLE

            } else {
                tv_firstLetterr.text = contacts?.get(position)?.name.toString()[0].toString().capitalize()
                iv_user_image.visibility = View.INVISIBLE
                tv_firstLetterr.visibility = View.VISIBLE
                ll_tvHolder.visibility = View.VISIBLE
                ll_tvHolder.setBackgroundResource(R.drawable.greyfilled_round)
                tv_firstLetterr.setTextColor(resources.getColor(R.color.white))
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
                val bundle = bundleOf(
                    KEY_USERID to contacts.get(position).id.toString(),
                    KEY_LEVEL_COLOUR to userLevel
                )

                findNavController().navigate(R.id.performTransferByContactFragment, bundle)
            }

        }

    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}