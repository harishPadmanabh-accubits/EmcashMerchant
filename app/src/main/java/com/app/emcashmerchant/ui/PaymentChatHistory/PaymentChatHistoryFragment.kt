package com.app.emcashmerchant.ui.PaymentChatHistory

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.GroupedChatHistoryResponse
import com.app.emcashmerchant.data.models.PaymentChatResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.PaymentChatHistory.adapter.ChatItemClickListener
import com.app.emcashmerchant.ui.PaymentChatHistory.adapter.PaymentChatHistoryAdapter
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.ui.transaction_history.adapters.AllTransactionAdapter
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.block_layout.*
import kotlinx.android.synthetic.main.dialog_emcash_successful.*
import kotlinx.android.synthetic.main.fragment_all_transactions.*
import kotlinx.android.synthetic.main.fragment_payment_chat_history.*
import kotlinx.android.synthetic.main.fragment_payment_chat_history.fl_user_level
import kotlinx.android.synthetic.main.fragment_payment_chat_history.iv_back
import kotlinx.android.synthetic.main.fragment_payment_chat_history.tv_name
import kotlinx.android.synthetic.main.fragment_payment_chat_history.tv_number
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PaymentChatHistoryFragment : Fragment(), ChatItemClickListener {

    lateinit var dialogBlockUnBlock: Dialog
    private lateinit var sessionStorage: SessionStorage
    private lateinit var viewModel: PaymentChatHistoryViewModel
    private lateinit var dialog: AppDialog
    private var isBlockedLoggedInUser: Boolean? = false
    private var isBlockedContactUser: Boolean? = false
    private var userId = ""
    var name: String? = null
    var phoneNumber: String? = null

    val pagedAdapter by lazy {
        PaymentChatHistoryAdapter(this)
    }

    companion object {
        fun newInstance() = PaymentChatHistoryFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_payment_chat_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentChatHistoryViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        dialog = AppDialog(requireActivity())

        userId = requireArguments().getString(KEY_USERID).toString()

        tv_balance.text = sessionStorage.balance

        viewModel.getPaymentChat(userId.toInt())

        observe(view)


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_paymentChatHistoryFragment_to_homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

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

        rv_chat.apply {
            adapter = pagedAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, true
            )
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getListData(userId.toInt()).collect {
                it?.let {
                    pagedAdapter.submitData(it)

                }
            }

        }

        iv_menu.setOnClickListener {
            showPopup(it, userId.toInt())
        }

        btn_request.setOnClickListener {
            var bundle = bundleOf(
                KEY_USERID to userId,
                KEY_NAME to name,
                KEY_NUMBER to phoneNumber


            )
            Navigation.findNavController(view)
                .navigate(
                    R.id.action_paymentChatHistoryFragment_to_paymentRequestByContactFragment,
                    bundle
                )


        }
        btn_pay.setOnClickListener {
            var bundle = bundleOf(
                KEY_USERID to userId,
                KEY_PAGE to "ChatScreen"


            )
            Navigation.findNavController(view)
                .navigate(
                    R.id.action_paymentChatHistoryFragment_to_performTransferByContactFragment,
                    bundle
                )

        }
        iv_back.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_paymentChatHistoryFragment_to_homeFragment)
        }

    }

    fun observe(view: View) {
        viewModel.apply {
            paymentChatStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        cl_main.visibility=View.VISIBLE
                        name = it.data?.contact?.name
                        phoneNumber = it.data?.contact?.phoneNumber
                        var userLevel = it.data?.contact?.ppp
                        var roleId = it.data?.contact?.roleId

                        if (it.data?.contact?.profileImage != null) {
                            iv_user_dpTop.visibility = View.VISIBLE
                            tv_firstLetterrTop.visibility = View.INVISIBLE
                            iv_user_dpTop.loadImageWithUrl(it.data?.contact?.profileImage.toString())

                        } else {
                            iv_user_dpTop.visibility = View.INVISIBLE
                            tv_firstLetterrTop.visibility = View.VISIBLE
                            tv_firstLetterrTop.text =
                                it.data?.contact?.name.toString()[0].toString()
                            fl_user_level.setBackgroundResource(R.drawable.black_round)

                        }

                        if (roleId == 3) {
                            if (userLevel == 1) {
                                fl_user_level.setBackgroundResource(R.drawable.green_round)
                            } else if (userLevel == 2) {
                                fl_user_level.setBackgroundResource((R.drawable.yellow_round))
                            } else if (userLevel == 4) {
                                fl_user_level.setBackgroundResource(R.drawable.red_round)

                            }
                        } else if (roleId == 2) {


                        }
                        tv_name.text = name
                        tv_number.text = phoneNumber
                        tv_balance.text = it.data?.wallet?.amount.toString()
                        sessionStorage.balance = it.data?.wallet?.amount.toString()


                        isBlockedContactUser = it.data?.contact?.isLoggedInUserBlockedContactUser
                        isBlockedLoggedInUser = it.data?.contact?.isContactUserBlockedLoggedInUser

                        if (isBlockedContactUser == false) {
                            ll_button_holder.visibility = View.VISIBLE


                        } else {
                            ll_button_holder.visibility = View.GONE


                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                    }

                }
            })
            blockStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        viewModel.getPaymentChat(userId.toInt())


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                    }

                }
            })

            unblockStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        viewModel.getPaymentChat(userId.toInt())


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                    }

                }
            })

        }

    }

    override fun onChatRejectClicked(payment: GroupedChatHistoryResponse.Data.Row.Transaction) {
        var bundle = bundleOf(
            KEY_REF_ID to payment.transactionId,
            KEY_PAGE to "ChatScreen",
            KEY_ACTION to "reject"
        )

        findNavController().navigate(R.id.paymentPinNumberFragment, bundle)
    }

    override fun onChatAcceptClicked(payment: GroupedChatHistoryResponse.Data.Row.Transaction) {
        var bundle = bundleOf(
            KEY_REF_ID to payment.transactionId,
            KEY_PAGE to "ChatScreen",
            KEY_ACTION to "accept"

        )

        findNavController().navigate(R.id.paymentPinNumberFragment, bundle)

    }

    private fun showPopup(view: View, userId: Int) {


        var popup = PopupMenu(requireContext(), view)
        popup = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            PopupMenu(context, view, Gravity.END, 40, R.style.MyPopupMenu)
        } else {
            PopupMenu(context, view)
        }
        popup.inflate(R.menu.action_menu)
        val menuOpts = popup.menu

        if (isBlockedContactUser == false) {
            menuOpts.getItem(2).title = "Block Account"


        } else {

            menuOpts.getItem(2).title = "Unblock Account"

        }


        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {

                R.id.help_support -> {
                    //help&support
                }
                R.id.block_account -> {
                    //block
                    dialogBlockUnBlock(userId)


                }
                R.id.refresh -> {
                    viewModel.getPaymentChat(userId)
                }

            }

            true
        })

        popup.show()
    }

    private fun dialogBlockUnBlock(userId: Int) {
        dialogBlockUnBlock = Dialog(requireActivity())
        dialogBlockUnBlock.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogBlockUnBlock.setContentView(R.layout.block_layout)
        dialogBlockUnBlock.setCancelable(true)
        dialogBlockUnBlock.setCanceledOnTouchOutside(true)
        dialogBlockUnBlock.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBlockUnBlock.show()

        dialogBlockUnBlock.tv_name.text = name
        dialogBlockUnBlock.tv_number.text = phoneNumber

        if (isBlockedContactUser == false) {
            dialogBlockUnBlock.tv_block.text = "Block"

        } else {
            dialogBlockUnBlock.tv_block.text = "Unblock"
        }

        dialogBlockUnBlock.cancel_lay.setOnClickListener {
            dialogBlockUnBlock.dismiss()
        }
        dialogBlockUnBlock.confirm_lay.setOnClickListener {
            dialogBlockUnBlock.dismiss()
            if (isBlockedContactUser == false) {
                viewModel.block(userId)

            } else {
                viewModel.unBlock(userId)

            }

        }


    }

}

