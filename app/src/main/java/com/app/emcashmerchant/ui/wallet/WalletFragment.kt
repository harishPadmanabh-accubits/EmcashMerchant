package com.app.emcashmerchant.ui.wallet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import com.app.emcashmerchant.data.models.WalletTransactionResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapter
import com.app.emcashmerchant.ui.wallet.adapter.WalletTransactionAdapterV2
import com.app.emcashmerchant.utils.extensions.dateFormat
import com.app.emcashmerchant.utils.extensions.getCurrentDate
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.wallet_fragment.*
import kotlinx.android.synthetic.main.wallet_fragment.btn_convert

class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var viewModel: WalletViewModel
    private lateinit var sessionStorage: SessionStorage
   // var transactionData = arrayListOf<WalletTransactionResponse.Data.Row>()

    var balance:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        tv_balance.text=sessionStorage.balance

        observe()
        viewModel.walletTransactions()

        btn_convert.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.convertToCashFragment)

        }

        btn_load_emcash.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loadEmcashFragment)
        }
        iv_back.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }

    }
    private fun observe() {
        viewModel.apply {
            walletTransactionStatus.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                    }
                    ApiCallStatus.SUCCESS -> {
                        balance=it.data?.wallet?.amount.toString()
                        sessionStorage.balance=balance
                        tv_balance.text=sessionStorage.balance
                        var currentDate  =  getCurrentDate()
                        var updateDate  = dateFormat(it.data?.rows?.get(1)?.updatedAt.toString())

                        //transactionData= it.data
                     val groupedActivies =   groupActivitiesByDate(it.data?.rows)

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
                    ApiCallStatus.ERROR -> {
                        requireActivity().showShortToast(it.errorMessage)

                    }
                }

            })
        }
    }


}