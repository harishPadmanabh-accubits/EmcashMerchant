package com.app.emcashmerchant.ui.home.home_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.utils.KEY_AMOUNT
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.widget.LevelProfileImageView
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.view.*
import kotlinx.android.synthetic.main.item_recent_payment.view.*
import kotlinx.android.synthetic.main.item_view_all.view.*
import java.lang.IllegalStateException

class RecentTransactionsAdapter(val transactions: List<RecentTransactionResponse.Data.Row>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_USER -> {
                RecentTransactionsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_recent_payment, parent, false)
                )
            }
            TYPE_VIEW_ALL -> {
                ViewAllViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_view_all, parent, false)
                )
            }
            else -> throw IllegalStateException("Invalid view type in recent payments Recycler View")
        }
    }

    override fun getItemViewType(position: Int): Int {
        POSITION_SIZE = transactions.size
        return if (position == transactions.size)
            TYPE_VIEW_ALL
        else
            TYPE_USER
    }


    override fun getItemCount(): Int = transactions.size.plus(1)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentTransactionsViewHolder -> {
                holder.bind(transactions[position])
            }
            is ViewAllViewHolder -> {
                holder.bind()

            }
        }
    }

    class RecentTransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(transaction: RecentTransactionResponse.Data.Row) {


            itemView.apply {
                lpi_imageView.setProfileName(transaction.name)
                if (transaction.roleId == 3) {
                    lpi_imageView.fl_user_level.visibility = View.VISIBLE

                    if (transaction.ppp == 1) {
                        lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.GREEN)

                    } else if (transaction.ppp == 2) {
                        lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.YELLOW)

                    } else if (transaction.ppp == 4) {
                        lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.RED)

                    }
                } else if (transaction.roleId == 2) {
                    lpi_imageView.fl_user_level.visibility = View.INVISIBLE


                }
                ll_contact.setOnClickListener {
                    val bundle = bundleOf(
                        KEY_USERID to transaction.userId.toString()
                    )
                    findNavController().navigate(
                        R.id.action_homeFragment_to_paymentChatHistoryFragment,
                        bundle
                    )

                }
            }
        }

    }

    class ViewAllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            if (POSITION_SIZE < 9) {
                itemView.apply {
                    cl_viewall.visibility = View.GONE
                }
            }
            itemView.apply {
                cl_viewall.setOnClickListener {
                    findNavController().navigate(R.id.viewAllTransactionsFragment)
                }

            }
        }

    }


}


const val TYPE_USER = 1000
const val TYPE_VIEW_ALL = 1001
var POSITION_SIZE = 0