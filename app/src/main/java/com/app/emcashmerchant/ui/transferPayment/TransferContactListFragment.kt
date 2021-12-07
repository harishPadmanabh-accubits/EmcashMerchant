package com.app.emcashmerchant.ui.transferPayment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.transferPayment.adapter.AllContactsTransferAdapter
import com.app.emcashmerchant.ui.transferPayment.adapter.RecentTransactionContactsAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_transfer_contact_list.*
import timber.log.Timber


class TransferContactListFragment : Fragment(R.layout.fragment_transfer_contact_list) {
    private lateinit var viewModel: TransferPaymentViewModel
    private lateinit var dialog: AppDialog
    val pagedAdapter by lazy {
        AllContactsTransferAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TransferPaymentViewModel::class.java)
        dialog = AppDialog(requireActivity())
        viewModel.getRecentTransactions()
        et_search_contact.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search.postValue(et_search_contact.text.toString())
                Timber.e("searched")
                return@OnEditorActionListener true
            }
            false
        })


        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }

        btn_scanQr.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.qrCodeScannerFragment)

        }

        pagedAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                dialog.show_dialog()
            } else {
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

        rv_all_contacts_list.apply {
            adapter = pagedAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
        }

        viewModel.pagedContacts.observe(viewLifecycleOwner, Observer {
            pagedAdapter.submitData(lifecycle, it)

        })

        observe(view)


    }

    fun observe(view: View) {
        viewModel.apply {

            recentTransactions.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        it.data?.rows?.let {
                            rv_recent_contacts_list.apply {
                                layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    RecyclerView.HORIZONTAL,
                                    false
                                )
                                adapter = RecentTransactionContactsAdapter(it, view)
                            }
                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()

                    }

                }
            })
        }
    }

}