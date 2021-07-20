package com.app.emcashmerchant.ui.wallet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapterV2
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.extensions.trimID
import kotlinx.android.synthetic.main.walletv2.*


class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var viewModel: WalletViewModel
    private lateinit var sessionStorage: SessionStorage
    // var transactionData = arrayListOf<WalletTransactionResponse.Data.Row>()
    lateinit var dialog: AppDialog

    var balance: String? = null
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
        dialog= AppDialog(requireActivity())
        tv_balance.text = sessionStorage.balance



        observe()
        viewModel.walletTransactions()

        btn_convert.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.convertToCashFragment)

        }

        btn_load_emcash.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loadEmcashFragment)
        }
        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

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
                        if (balance != null) {
                            sessionStorage.balance = balance
                            tv_balance.text = sessionStorage.balance
                        }
                        tv_safe_box_id.text= "Wallet ID : ".plus(trimID(it.data?.wallet?.id.toString()))
                        //transactionData= it.data
                        it.data?.rows.let {
                            val groupedActivies = groupActivitiesByDate(it)
                            rv_wallet_transaction.apply {
                                layoutManager = LinearLayoutManager(
                                    requireActivity(),
                                    RecyclerView.VERTICAL, false
                                )
                                itemAnimator = DefaultItemAnimator()
                                //   adapter=WalletTransactionAdapter(transactionData)
                                adapter = WalletTransactionAdapterV2(
                                    groupedActivies
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