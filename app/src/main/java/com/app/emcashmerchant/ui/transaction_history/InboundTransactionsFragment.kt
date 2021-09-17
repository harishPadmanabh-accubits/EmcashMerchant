package com.app.emcashmerchant.ui.transaction_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.transaction_history.adapters.AllTransactionAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_all_transactions.*
import kotlinx.android.synthetic.main.fragment_inbound_transactions.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class InboundTransactionsFragment : Fragment() {

    private lateinit var dialog:AppDialog

    val pagedAdapter by lazy {
        AllTransactionAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inbound_transactions, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog= AppDialog(requireActivity())
        pagedAdapter.addLoadStateListener {loadState ->
            if (loadState.refresh is LoadState.Loading){
            }
            else{
//                iv_emptyInboundTransaction.visibility=View.VISIBLE
//                rv_inbound.visibility=View.GONE
                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                error?.let {
                    requireActivity().showShortToast(it.error.message)
                }
            }
        }


        rv_inbound.apply {
            adapter=pagedAdapter
            layoutManager=LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
        }





    }

    override fun onResume() {
        super.onResume()
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.apply {
            setScreenFlag(HistoryScreens.INBOUND)
            filter.value = HistoryFilter(mode = "1")
            sendType(true)

            pagedInboundTransactions.observe(viewLifecycleOwner, Observer {
                pagedAdapter.submitData(lifecycle,it)
                Timber.e("Observing ${it}")
            })


        }

    }


}