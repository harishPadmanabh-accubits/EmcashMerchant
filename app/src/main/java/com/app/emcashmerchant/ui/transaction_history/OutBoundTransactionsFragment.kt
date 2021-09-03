package com.app.emcashmerchant.ui.transaction_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.transaction_history.adapters.AllTransactionAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_out_bound_transactions.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OutBoundTransactionsFragment : Fragment() {
    private lateinit var viewModel: TransactionHistoryViewModel
    private lateinit var dialog: AppDialog
    val pagedAdapter by lazy {
        AllTransactionAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_out_bound_transactions, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        dialog= AppDialog(requireActivity())

        pagedAdapter.addLoadStateListener {loadState ->
            if (loadState.refresh is LoadState.Loading){
            }
            else{

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


        rv_outbound.apply {
            adapter=pagedAdapter
            layoutManager=LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.outBoundTransactions.collect {
                pagedAdapter.submitData(it)
            }

        }



        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.apply {
            status.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//                viewModel.getAllGroupedTransactions("0","",it,"","")

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getListData("2", "", "", it, "").collectLatest {
                        pagedAdapter.submitData(it)
                    }


                }


            })
            date.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//                viewModel.getAllGroupedTransactions("0","","",it[0], it[1])

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getListData("2", it[0], it[1], "", "").collectLatest {
                        pagedAdapter.submitData(it)

                    }

                }


            })

        }
    }

    private fun getViewModel() {
        viewModel = requireActivity().obtainViewModel(TransactionHistoryViewModel::class.java)

    }

}
