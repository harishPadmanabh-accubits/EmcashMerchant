package com.app.emcashmerchant.ui.transfer_payment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.utils.BUCKET_URL
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.widget.LevelProfileImageView
import kotlinx.android.synthetic.main.item_recent_payment.view.*
import kotlinx.android.synthetic.main.item_recent_payment_contacts.view.*
import kotlinx.android.synthetic.main.item_recent_payment_contacts.view.ll_contact
import kotlinx.android.synthetic.main.item_recent_payment_contacts.view.lpi_imageView
import kotlinx.android.synthetic.main.layout_item_recent_payment.view.*


class RecentTransactionContactsAdapter(
    private val transaction: List<RecentTransactionResponse.Data.Row>,
    private val view: View
) : RecyclerView.Adapter<RecentTransactionContactsAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recent_payment_contacts, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            if (transaction[position].profileImage != null) {
                lpi_imageView.setProfileImage(BUCKET_URL + transaction[position].profileImage.toString())
                iv_user_image.visibility = View.VISIBLE
                tv_user_name_letter.visibility = View.INVISIBLE


            } else {
                lpi_imageView.setFirstLetter(transaction[position].name[0].toString())
                iv_user_image.visibility = View.INVISIBLE
                tv_user_name_letter.visibility = View.VISIBLE
                lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.BLACK)

            }
            if (transaction[position].roleId == 3) {
                lpi_imageView.fl_user_level.visibility = View.VISIBLE

                if (transaction[position].ppp == 1) {
                    lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.GREEN)

                } else if (transaction[position].ppp == 2) {
                    lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.YELLOW)

                } else if (transaction[position].ppp == 4) {
                    lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.RED)

                }
            } else if (transaction[position].roleId == 2) {
                lpi_imageView.fl_user_level.visibility = View.INVISIBLE


            }

            lpi_imageView.setProfileName(transaction[position].name)
            ll_contact.setOnClickListener {
                val bundle = bundleOf(
                    KEY_USERID to transaction[position].userId.toString()
                )

                Navigation.findNavController(view)
                    .navigate(R.id.performTransferByContactFragment, bundle)
            }
        }

    }

    override fun getItemCount(): Int {
        return transaction.size
    }


}