package com.app.emcashmerchant.ui.transaction_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.transaction_history.adapters.AllTransactionAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_out_bound_transactions.*

class OutBoundTransactionsFragment : Fragment() {
    private lateinit var viewModel: TransactionHistoryViewModel
    private lateinit var dialog: AppDialog

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
        observe()
        viewModel.getGroupedOutBoundTransactions("2","","","","")

        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.apply {
            status.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                viewModel.getGroupedOutBoundTransactions("2","",it,"","")

            })
            date.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                viewModel.getGroupedOutBoundTransactions("2","","",it[0], it[1])

            })
        }
    }

    private fun getViewModel() {
        viewModel = requireActivity().obtainViewModel(TransactionHistoryViewModel::class.java)

    }
    private fun observe() {
        viewModel.apply {
            groupedOutBoundtransactionHistoryResponse.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                    }
                    ApiCallStatus.SUCCESS -> {
                        it.data?.rows?.let {
                            rv_outbound.apply {
                                layoutManager = LinearLayoutManager(
                                    requireActivity(),
                                    RecyclerView.VERTICAL, false
                                )
                                itemAnimator = DefaultItemAnimator()
                                adapter = AllTransactionAdapter(
                                    it
                                )
                            }
                        }


                    }

                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(it.errorMessage)

                    }
                }

            })
        }
    }

}
