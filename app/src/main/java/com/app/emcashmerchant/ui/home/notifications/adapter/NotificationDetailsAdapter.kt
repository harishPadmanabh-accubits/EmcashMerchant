package com.app.emcashmerchant.ui.home.notifications.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.GroupedNotificationResponse
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.NotificationTYPE.MERCHANT_DOCUMENTS_REJECTED_NOTIFICATION
import com.app.emcashmerchant.utils.NotificationTYPE.MERCHANT_DOCUMENTS_VERIFIED_NOTIFICATION
import com.app.emcashmerchant.utils.NotificationTYPE.PENDING_NOTIFICATION
import com.app.emcashmerchant.utils.NotificationTYPE.REGISTRATION_COMPLETED
import com.app.emcashmerchant.utils.NotificationTYPE.REJECTED_NOTIFICATION
import com.app.emcashmerchant.utils.NotificationTYPE.SUCCESS_NOTIFICATION
import com.app.emcashmerchant.utils.extensions.timeFormat
import kotlinx.android.synthetic.main.item_notification_details.view.*

class NotificationDetailsAdapter(val data: List<GroupedNotificationResponse.Data.Row.Notification>) :
    RecyclerView.Adapter<NotificationDetailsAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_notification.text = data[position].message
            tv_time.text = timeFormat(data[position].createdAt)
            val type = data[position].type
            ll_notifications.setOnClickListener {

                if (type == PENDING_NOTIFICATION || type == SUCCESS_NOTIFICATION || type == REJECTED_NOTIFICATION) {
                    val bundle = bundleOf(
                        KEY_USERID to data[position].contactUserId.toString()
                    )

                    findNavController().navigate(
                        R.id.action_notificationsFragment_to_paymentChatHistoryFragment,
                        bundle
                    )

                }

            }
            when (type) {
                PENDING_NOTIFICATION -> {
                    iv_point.setColorFilter(ContextCompat.getColor(context, R.color.orange))
                }
                SUCCESS_NOTIFICATION -> {
                    iv_point.setColorFilter(ContextCompat.getColor(context, R.color.green))
                }
                REJECTED_NOTIFICATION -> {
                    iv_point.setColorFilter(ContextCompat.getColor(context, R.color.red))
                }
                MERCHANT_DOCUMENTS_VERIFIED_NOTIFICATION -> {
                    iv_point.setColorFilter(ContextCompat.getColor(context, R.color.green))
                }
                REGISTRATION_COMPLETED -> {
                    iv_point.setColorFilter(ContextCompat.getColor(context, R.color.sky_blue))
                }
                MERCHANT_DOCUMENTS_REJECTED_NOTIFICATION -> {
                    iv_point.setColorFilter(ContextCompat.getColor(context, R.color.red))
                }
            }


        }
    }
}