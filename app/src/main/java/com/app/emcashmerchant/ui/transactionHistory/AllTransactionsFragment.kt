package com.app.emcashmerchant.ui.transactionHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.transactionHistory.adapters.AllTransactionAdapter
import com.app.emcashmerchant.ui.transactionHistory.screenEnumHandler.HistoryScreens
import com.app.emcashmerchant.ui.transactionHistory.model.HistoryFilter
import kotlinx.android.synthetic.main.fragment_all_transactions.*
import timber.log.Timber


class AllTransactionsFragment : Fragment(R.layout.fragment_all_transactions)
   {

    private val pagedAdapter by lazy {
        AllTransactionAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pagedAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && pagedAdapter.itemCount < 1) {
                iv_emptyTransaction.visibility = View.VISIBLE
                rv_all_transactions.visibility = View.GONE
            } else {
                iv_emptyTransaction.visibility = View.GONE
                rv_all_transactions.visibility = View.VISIBLE
            }
        }





        rv_all_transactions.apply {
            adapter = pagedAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
        }

    }


    override fun onResume() {
        super.onResume()
        val transactionHistoryViewModel =
            ViewModelProvider(requireActivity()).get(TransactionHistoryViewModel::class.java)
        transactionHistoryViewModel.apply {
            sendType(true)
            setScreenFlag(HistoryScreens.ALL)
            filter.value = HistoryFilter()
            pagedTransactions.observe(viewLifecycleOwner, Observer {
                pagedAdapter.submitData(lifecycle, it)
                Timber.e("Observing ${it}")
            })

        }
    }




}