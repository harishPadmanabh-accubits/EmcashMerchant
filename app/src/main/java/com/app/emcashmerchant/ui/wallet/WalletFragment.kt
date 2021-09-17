package com.app.emcashmerchant.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapterV2
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.loadImageWithUrlUser
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.extensions.trimID
import kotlinx.android.synthetic.main.walletv2.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    val pagedAdapter by lazy {
        WalletTransactionAdapterV2()
    }
    private lateinit var viewModel: WalletViewModel
    private lateinit var sessionStorage: SessionStorage

    lateinit var dialog: AppDialog

    var balance: String? = null
    lateinit var mLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)

        return inflater.inflate(R.layout.walletv2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        dialog = AppDialog(requireActivity())
        tv_balance.text = sessionStorage.balance

        mLayoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL, false
        )

        observe()
        viewModel.walletTransactions(1, 10)
//        viewModel.groupedWalletTransactions(page, limit)

        btn_convert.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.convertToCashFragment)

        }

        btn_load_emcash.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loadEmcashFragment)
        }
        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }

        pagedAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                dialog.show_dialog()
            }
//            else if (loadState.refresh is LoadState.NotLoading && pagedAdapter.itemCount < 1) {
//                dialog.dismiss_dialog()
//                iv_emptyTransaction.visibility=View.VISIBLE
//                rv_wallet_transaction.visibility=View.GONE
//            }
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

        rv_wallet_transaction.apply {
            adapter = pagedAdapter
            layoutManager = mLayoutManager
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.walletActivities.collect {
                pagedAdapter.submitData(it)
            }

        }


    }

    private fun observe() {
        viewModel.apply {

            walletTransactionStatus.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        balance = it.data?.wallet?.amount.toString()
                        appCompatImageView.loadImageWithUrlUser(it.data?.wallet?.user?.profileImage)

                        if (balance != null) {
                            sessionStorage.balance = balance
                            tv_balance.text = sessionStorage.balance
                        }
                        tv_safe_box_id.text =
                            "Wallet ID : ".plus(trimID(it.data?.wallet?.id.toString()))
                        //transactionData= it.data


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