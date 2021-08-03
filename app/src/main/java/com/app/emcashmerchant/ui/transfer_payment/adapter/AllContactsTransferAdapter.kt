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
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.contact_item.view.fl_user_level
import kotlinx.android.synthetic.main.contact_item.view.iv_user_image
import kotlinx.android.synthetic.main.contact_item.view.ll_contact
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.*
import kotlinx.android.synthetic.main.item_recent_payment.view.*
import kotlinx.android.synthetic.main.layout_item_recent_payment.view.*

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

            tv_contact_name.text=contacts?.get(position)?.name
            tv_contact_number.text=contacts?.get(position)?.phoneNumber
            ll_contact.setOnClickListener {
                val bundle = bundleOf(
                    KEY_USERID to contacts.get(position).id,
                    KEY_LEVEL_COLOUR to userLevel
                )

                Navigation.findNavController(view).navigate(R.id.performTransferByContactFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}