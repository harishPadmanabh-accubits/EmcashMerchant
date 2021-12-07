package com.app.emcashmerchant.ui.viewAllTransaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.viewAllTransaction.adapter.ViewAllTransactionsAdapterV2
import com.app.emcashmerchant.ui.viewAllTransaction.viewModel.AllTransactionsViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_view_all_transactions.*
import kotlinx.android.synthetic.main.fragment_view_all_transactions.iv_back
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ViewAllTransactionsFragment : Fragment(R.layout.fragment_view_all_transactions) {
    private lateinit var viewModel: AllTransactionsViewModel
    lateinit var dialog: AppDialog
    val pagedAdapter by lazy {
        ViewAllTransactionsAdapterV2()
    }
    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AllTransactionsViewModel::class.java)
        dialog = AppDialog(requireActivity())

        mLayoutManager = GridLayoutManager(requireContext(), 5)


        pagedAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                dialog.show_dialog()
            }

            else {
                dialog.dismiss_dialog()

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

        rv_all_transactions.apply {
            adapter = pagedAdapter
            layoutManager = mLayoutManager
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewAllActivities.collect {
                pagedAdapter.submitData(it)
            }

        }

        iv_back.setOnClickListener {

            findNavController().popBackStack()

        }

    }



}