package com.app.emcashmerchant.ui.home.notifications.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.DemoNotificationResponse
import kotlinx.android.synthetic.main.item_notification_details.view.*

class NotificationDetailsAdapter(val data : List<DemoNotificationResponse>): RecyclerView.Adapter<NotificationDetailsAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationDetailsAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification_details, parent, false)
        return NotificationDetailsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NotificationDetailsAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_notification.text=data[position].notification
            tv_time.text=data[position].time

            if(data[position].type==1){
                iv_point.setColorFilter(
                    ContextCompat.getColor(context,
                        R.color.sky_blue))
            }else if(data[position].type==2){
                iv_point.setColorFilter(
                    ContextCompat.getColor(context,
                        R.color.orange))
            }else if(data[position].type==3){
                iv_point.setColorFilter(
                    ContextCompat.getColor(context,
                        R.color.purple))
            }

        }
    }
}