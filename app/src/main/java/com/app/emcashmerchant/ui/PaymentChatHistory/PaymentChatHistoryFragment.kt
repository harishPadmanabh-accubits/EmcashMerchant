package com.app.emcashmerchant.ui.PaymentChatHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.PaymentChatHistory.adapter.PaymentChatHistoryAdapter
import com.app.emcashmerchant.ui.convertEmcashTocash.ConvertEmcashViewModel
import com.app.emcashmerchant.ui.payment_request.adapter.AllContactsRequestAdapter
import com.app.emcashmerchant.utils.KEY_AMOUNT
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_payment_chat_history.*
import kotlinx.android.synthetic.main.fragment_payment_chat_history.iv_back
import kotlinx.android.synthetic.main.fragment_request_contact_list.*


class PaymentChatHistoryFragment : Fragment() {

    private lateinit var sessionStorage: SessionStorage
    private lateinit var viewModel: PaymentChatHistoryViewModel

    companion object {
        fun newInstance() = PaymentChatHistoryFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        return inflater.inflate(R.layout.fragment_payment_chat_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentChatHistoryViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())

        var userId = requireArguments().getString(KEY_USERID).toString()

        viewModel.getPaymentChat(userId.toInt())
        tv_balance.text=sessionStorage.balance

        observe(view)

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }

    }

    fun observe(view: View) {
        viewModel.apply {
            paymentChatStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {

                    }
                    ApiCallStatus.SUCCESS -> {
                        tv_name.text=it.data?.contact?.name
                        tv_number.text=it.data?.contact?.phoneNumber

                        it.data?.rows.let {
                            val groupedActivies = groupPaymentTransactionsByDate(it)
                            rv_chat.apply {
                                layoutManager = LinearLayoutManager(
                                    requireActivity(),
                                    RecyclerView.VERTICAL, false
                                )
                                itemAnimator = DefaultItemAnimator()
                                adapter = PaymentChatHistoryAdapter(groupedActivies)
                            }
                        }


                    }
                    ApiCallStatus.ERROR -> {

                    }

                }
            })
        }

    }


}