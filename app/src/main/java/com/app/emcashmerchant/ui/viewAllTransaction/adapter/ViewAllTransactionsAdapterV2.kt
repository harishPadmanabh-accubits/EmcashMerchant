package com.app.emcashmerchant.ui.viewAllTransaction.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.GroupedWalletTransactionResponse
import com.app.emcashmerchant.data.models.RecentTransactionResponse
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapterV2
import com.app.emcashmerchant.utils.BUCKET_URL
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.widget.LevelProfileImageView
import kotlinx.android.synthetic.main.fragment_perform_transfer_by_contact.view.*
import kotlinx.android.synthetic.main.item_recent_payment.view.*
import kotlinx.android.synthetic.main.layout_item_recent_payment.view.*
import kotlinx.android.synthetic.main.layout_item_recent_payment.view.fl_user_level

class ViewAllTransactionsAdapterV2(): PagingDataAdapter
<RecentTransactionResponse.Data.Row, ViewAllTransactionsAdapterV2.ViewHolder>(
   DiffUtilCallBack()
) {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllTransactionsAdapterV2.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_payment, parent, false)
        return ViewAllTransactionsAdapterV2.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewAllTransactionsAdapterV2.ViewHolder, position: Int) {

        val currentActivity = getItem(position)


        currentActivity?.let {
            holder.itemView.apply {
                lpi_imageView.setProfileName(it.name)
                if(it.profileImage!=null){
                    lpi_imageView.setProfileImage(BUCKET_URL +it.profileImage.toString())
                    iv_user_image.visibility=View.VISIBLE
                    tv_user_name_letter.visibility=View.INVISIBLE


                }else{
                    lpi_imageView.setFirstLetter(it.name[0].toString())
                    iv_user_image.visibility=View.INVISIBLE
                    tv_user_name_letter.visibility=View.VISIBLE
                    lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.BLACK)

                }
                lpi_imageView.setProfileName(it.name)
                if (it.roleId == 3) {
                    lpi_imageView.fl_user_level.visibility = View.VISIBLE

                    if (it.ppp == 1) {
                        lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.GREEN)

                    } else if (it.ppp == 2) {
                        lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.YELLOW)

                    } else if (it.ppp == 4) {
                        lpi_imageView.setLevel(LevelProfileImageView.UserProfileLevel.RED)

                    }
                }

                ll_contact.setOnClickListener {
                    val bundle = bundleOf(
                        KEY_USERID to currentActivity.userId.toString()
                    )
                    findNavController().navigate(R.id.action_viewAllTransactionsFragment_to_paymentChatHistoryFragment,bundle)

                }
            }

        }

    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<RecentTransactionResponse.Data.Row>() {
        override fun areItemsTheSame(
            oldItem: RecentTransactionResponse.Data.Row,
            newItem: RecentTransactionResponse.Data.Row
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: RecentTransactionResponse.Data.Row,
            newItem: RecentTransactionResponse.Data.Row
        ): Boolean {
            return false


        }

    }
}