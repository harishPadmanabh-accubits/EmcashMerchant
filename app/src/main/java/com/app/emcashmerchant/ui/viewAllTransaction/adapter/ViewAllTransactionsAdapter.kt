package com.app.emcashmerchant.ui.viewAllTransaction.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.utils.widget.LevelProfileImageView
import kotlinx.android.synthetic.main.item_recent_payment.view.*
import kotlinx.android.synthetic.main.layout_item_recent_payment.view.*

class ViewAllTransactionsAdapter(val transaction: List<RecentTransactionResponse.Data.Row>): RecyclerView.Adapter<ViewAllTransactionsAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllTransactionsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_payment, parent, false)
        return ViewAllTransactionsAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewAllTransactionsAdapter.ViewHolder, position: Int) {
       holder.itemView.apply {
            lpi_imageView.setProfileName(transaction[position].name)
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
            ll_contact.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_paymentChatHistoryFragment)

            }
        }

    }

    override fun getItemCount(): Int {
        return  transaction.size
    }

}