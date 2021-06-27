package com.app.emcashmerchant.ui.home.home_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R

class RecentPaymentsAdapter : RecyclerView.Adapter<RecentPaymentsAdapter.ViewHolder>() {
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recent_payment, parent, false)
        )
    }

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


}