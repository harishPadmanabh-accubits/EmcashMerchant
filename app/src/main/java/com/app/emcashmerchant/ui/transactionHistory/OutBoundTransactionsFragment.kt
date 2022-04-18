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
import kotlinx.android.synthetic.main.fragment_out_bound_transactions.*
import timber.log.Timber

class OutBoundTransactionsFragment : Fragment(R.layout.fragment_out_bound_transactions){
    private val pagedAdapter by lazy {
        AllTransactionAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            pagedAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && pagedAdapter.itemCount < 1) {
                    iv_emptyOutboundTransaction.visibility = View.VISIBLE
                    rv_outbound.visibility = View.GONE
                } else {
                    iv_emptyOutboundTransaction.visibility = View.GONE
                    rv_outbound.visibility = View.VISIBLE
                }
            }





        rv_outbound.apply {
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
            setScreenFlag(HistoryScreens.OUTBOUND)
            filter.value = HistoryFilter(mode = "2")
            sendType(true)

            pagedOutboundTransactions.observe(viewLifecycleOwner, Observer {
                pagedAdapter.submitData(lifecycle, it)
            })

        }
    }


   
}
