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
import kotlinx.android.synthetic.main.fragment_all_transactions.*


class AllTransactionsFragment : Fragment() {
    private lateinit var viewModel: TransactionHistoryViewModel
    private lateinit var dialog: AppDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_all_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        dialog = AppDialog(requireActivity())
//        viewModel.getAllTransactions("0","","","","")
        viewModel.getAllGroupedTransactions("0","","","","")

        observe()



        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.apply {
            status.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                viewModel.getAllGroupedTransactions("0","",it,"","")

            })
            date.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                viewModel.getAllGroupedTransactions("0","","",it[0], it[1])

            })

        }


    }


    private fun getViewModel() {
        viewModel = requireActivity().obtainViewModel(TransactionHistoryViewModel::class.java)
    }

    private fun observe() {
        viewModel.apply {
            allGroupedtransactionHistoryResponse.observe(
                requireActivity(),
                androidx.lifecycle.Observer {
                    when (it.status) {
                        ApiCallStatus.LOADING -> {
                            dialog.show_dialog()
                        }
                        ApiCallStatus.SUCCESS -> {
                            dialog.dismiss_dialog()
                            it.data?.rows?.let {
                                rv_all_transactions.apply {
                                    layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL, false)
                                    adapter = AllTransactionAdapter(it)
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