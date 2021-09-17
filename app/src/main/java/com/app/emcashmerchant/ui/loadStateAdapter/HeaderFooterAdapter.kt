package com.app.emcashmerchant.ui.loadStateAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import kotlinx.android.synthetic.main.load_state_view.view.*

class HeaderFooterAdapter() : LoadStateAdapter<HeaderFooterAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        val progress = holder.itemView.animation_view
        val txtErrorMessage = holder.itemView.tv_load_state_errorMessage

        txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error){
            txtErrorMessage.text = loadState.error.localizedMessage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_view, parent, false)
        )
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
}