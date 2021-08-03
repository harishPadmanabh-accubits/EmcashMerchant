package com.app.emcashmerchant.ui.viewAllTransaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.viewAllTransaction.adapter.ViewAllTransactionsAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_view_all_transactions.*


class ViewAllTransactionsFragment : Fragment() {
    private lateinit var viewModel: AllTransactionsViewModel
    lateinit var dialog: AppDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return inflater.inflate(R.layout.fragment_view_all_transactions, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllTransactionsViewModel::class.java)
        dialog = AppDialog(requireActivity())


        viewModel.getAllTransactions()

        iv_back.setOnClickListener {

            findNavController().popBackStack()

        }
        observe()
    }

    private fun observe() {
        viewModel.apply {

            allTransactions.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it.data?.let {
                            rv_all_transactions.apply {
                                layoutManager = GridLayoutManager(requireContext(), 5)
                                adapter = ViewAllTransactionsAdapter(it.rows)
                            }
                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)
                    }
                }
            })


        }
    }

}