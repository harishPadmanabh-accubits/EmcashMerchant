package com.app.emcashmerchant.ui.home.notifications.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.ImageViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.DemoNotificationResponse
import com.app.emcashmerchant.data.models.GroupedNotificationResponse
import com.app.emcashmerchant.data.models.NotificationResponse
import com.app.emcashmerchant.data.models.PaymentChatResponse
import com.app.emcashmerchant.utils.KEY_ACTION
import com.app.emcashmerchant.utils.KEY_PAGE
import com.app.emcashmerchant.utils.KEY_REF_ID
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.extensions.timeformat
import kotlinx.android.synthetic.main.item_notification_details.view.*
import kotlinx.android.synthetic.main.layout_payment_reciept_top.*

class NotificationDetailsAdapter(val data: List<GroupedNotificationResponse.Data.Row.Notification>) :
    RecyclerView.Adapter<NotificationDetailsAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationDetailsAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification_details, parent, false)
        return NotificationDetailsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NotificationDetailsAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_notification.text = data[position].message
            tv_time.text = timeformat(data[position].createdAt)
            var type = data[position].type
            ll_notifications.setOnClickListener {
                if (type != 5 || type != 6) {
                    var bundle = bundleOf(
                        KEY_USERID to data[position].contactUserId.toString()
                    )

                    findNavController().navigate(
                        R.id.action_notificationsFragment_to_paymentChatHistoryFragment,
                        bundle
                    )


                }

            }
            if (type == 1) {//pending
                iv_point.setColorFilter(ContextCompat.getColor(context, R.color.orange));
            } else if (type == 2) {//success
                iv_point.setColorFilter(ContextCompat.getColor(context, R.color.green));
            } else if (type == 3) {//rejected
                iv_point.setColorFilter(ContextCompat.getColor(context, R.color.red));
            } else if (type == 4) {//documents verified
                iv_point.setColorFilter(ContextCompat.getColor(context, R.color.green));
            } else if (type == 6) {//registration completed
                iv_point.setColorFilter(ContextCompat.getColor(context, R.color.sky_blue));
            } else if (type == 5) {//rejected from merchant side
                iv_point.setColorFilter(ContextCompat.getColor(context, R.color.red));
            }


        }
    }
}