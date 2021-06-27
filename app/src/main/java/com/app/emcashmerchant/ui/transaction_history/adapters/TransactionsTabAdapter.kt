package com.app.emcashmerchant.ui.transaction_history.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.emcashmerchant.ui.transaction_history.AllTransactionsFragment
import com.app.emcashmerchant.ui.transaction_history.InboundTransactionsFragment
import com.app.emcashmerchant.ui.transaction_history.OutBoundTransactionsFragment
import java.lang.Exception

class TransactionsTabAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0->{
                AllTransactionsFragment()
            }
            1->{
                InboundTransactionsFragment()
            }
            2->{
                OutBoundTransactionsFragment()
            }
            else -> throw Exception("Illegal positon in Tab layout")
        }
    }

}