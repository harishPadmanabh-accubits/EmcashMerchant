package com.app.emcashmerchant.ui.transaction_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.DummyTransactionModel
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.transaction_history.adapters.AllTransactionAdapter
import com.app.emcashmerchant.ui.transaction_history.adapters.TransactionFilter
import com.app.emcashmerchant.ui.transaction_history.adapters.TransactionHistoryAdapter
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapterV2
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_all_transactions.*
import kotlinx.android.synthetic.main.walletv2.*


class AllTransactionsFragment : Fragment() {
    private lateinit var viewModel: TransactionHistoryViewModel
    private lateinit var dialog:AppDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_all_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        dialog= AppDialog(requireActivity())
        viewModel.getAllTransactions("0")
        observe()
//        setupRecycelerView(viewModel.transactionHistory)
    }
//
//    private fun setupRecycelerView(transactionHistory: List<DummyTransactionModel>) {
//        rv_all_transactions.adapter = TransactionHistoryAdapter(transactionHistory).also {
//            it.transactionType = TransactionFilter.ALL
//        }
//    }

    private fun getViewModel() {
        viewModel = requireActivity().obtainViewModel(TransactionHistoryViewModel::class.java)
    }

    private fun observe() {
        viewModel.apply {
            alltransactionHistoryResponse.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it.data?.rows.let {
                            val groupedTransactions = groupTransactionActivitiesByDate(it)
                            rv_all_transactions.apply {
                                layoutManager = LinearLayoutManager(
                                    requireActivity(),
                                    RecyclerView.VERTICAL, false
                                )
                                itemAnimator = DefaultItemAnimator()
                                adapter = AllTransactionAdapter(
                                    groupedTransactions
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