package com.app.emcashmerchant.ui.home.homeScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.response.RecentTransactionResponse
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.widget.LevelProfileImageView
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.view.fl_user_level
import kotlinx.android.synthetic.main.item_recent_payment.view.ll_contact
import kotlinx.android.synthetic.main.item_recent_payment.view.lpi_imageView
import kotlinx.android.synthetic.main.item_view_all.view.*
import kotlinx.android.synthetic.main.layout_item_recent_payment.view.*
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
                if(transaction.profileImage!=null)
                {
                    lpi_imageView.setProfileImage(BUCKET_URL+transaction.profileImage.toString())
                    iv_user_image.visibility=View.VISIBLE
                    tv_user_name_letter.visibility=View.INVISIBLE
                    ll_tvHolder.visibility=View.INVISIBLE


                }
                else{
                    lpi_imageView.setFirstLetter(transaction.name[0].toString())
                    iv_user_image.visibility=View.INVISIBLE
                    tv_user_name_letter.visibility=View.VISIBLE
                    ll_tvHolder.visibility=View.VISIBLE
                    ll_tvHolder.setBackgroundResource(R.drawable.greyfilled_round)

                }

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
                }

                ll_contact.setOnClickListener {
                    val bundle = bundleOf(
                        KEY_USERID to transaction.userId.toString(),
                        KEY_LEVEL_COLOUR to transaction.ppp.toString(),
                        KEY_PROFLE_IMAGE_LINK to transaction.profileImage

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
            }else{
                itemView.apply {
                    cl_viewall.visibility = View.VISIBLE
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