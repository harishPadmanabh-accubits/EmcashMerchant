package com.app.emcashmerchant.ui.transaction_history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.transaction_history.adapters.TransactionsTabAdapter
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.transaction_history_fragment.*
import timber.log.Timber

class TransactionHistoryFragment : Fragment() {

    companion object {
        fun newInstance() =
            TransactionHistoryFragment()
    }

    private lateinit var viewModel: TransactionHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transaction_history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel(requireActivity())
        setupTabs()
        setupViews(view)

    }

    private fun initViewModel(fragmentActivity: FragmentActivity?) {
        if (fragmentActivity != null)
            fragmentActivity.obtainViewModel(TransactionHistoryViewModel::class.java)
        else
            Timber.e("ViewModel init failed")

    }

    private fun setupViews(view: View) {
        iv_back.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
        iv_filter.setOnClickListener {
            //setup bottom sheet
        }
    }

    private fun setupTabs() {
        viewpager_tabs.adapter = TransactionsTabAdapter(requireActivity())
        TabLayoutMediator(tab_layout, viewpager_tabs) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.all)
                1 -> tab.text = getString(R.string.inbound)
                2 -> tab.text = getString(R.string.outbound)
            }
        }.attach()
    }


}