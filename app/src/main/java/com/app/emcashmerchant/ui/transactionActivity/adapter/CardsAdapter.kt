package com.app.emcashmerchant.ui.transactionActivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.CardResponse
import kotlinx.android.synthetic.main.item_payment_account.view.*


class CardsAdapter(val list: ArrayList<CardResponse>) :
    RecyclerView.Adapter<CardsAdapter.ViewHolder>() {
    private var selectedId = 0
    private var defaultCardPos = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_payment_account, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: CardsAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_card.text = list[position].cardnumber
            tv_amount.text = list[position].amount

            val currentItem = list[position]

            holder.itemView.apply {

                tv_card.text = currentItem.cardnumber
                tv_amount.text = currentItem.amount

                if (currentItem.default) {
                    selectedId = currentItem.id
                    defaultCardPos = position
                }
                ll_item_main.setOnClickListener {
                    selectedId = currentItem.id
                    list[defaultCardPos].default = false
                    notifyDataSetChanged()
                }

                if (selectedId == currentItem.id)
                    selectCard(this)
                else
                    unSelectCard(this)
            }
        }
    }

    private fun selectCard(itemView: View) {
        itemView.apply {
            rb_select.isChecked = true
            ll_item_main.setBackgroundResource(R.drawable.grey_rounded_bg)
        }
    }

    private fun unSelectCard(itemView: View) {
        itemView.apply {
            rb_select.isChecked = false
            ll_item_main.setBackgroundResource(R.color.white)
        }
    }


}