package com.app.emcashmerchant.ui.paymentChatHistory

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.response.GroupedChatHistoryResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.paymentChatHistory.ViewModel.PaymentChatHistoryViewModel
import com.app.emcashmerchant.ui.paymentChatHistory.adapter.ChatItemClickListener
import com.app.emcashmerchant.ui.paymentChatHistory.adapter.PaymentChatHistoryAdapter
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.checkNetwork
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.block_layout.*
import kotlinx.android.synthetic.main.fragment_payment_chat_history.*
import kotlinx.android.synthetic.main.fragment_payment_chat_history.fl_user_level
import kotlinx.android.synthetic.main.fragment_payment_chat_history.iv_back
import kotlinx.android.synthetic.main.fragment_payment_chat_history.tv_name
import kotlinx.android.synthetic.main.fragment_payment_chat_history.tv_number


class PaymentChatHistoryFragment : Fragment(R.layout.fragment_payment_chat_history),
    ChatItemClickListener {

    lateinit var dialogBlockUnBlock: Dialog
    private lateinit var sessionStorage: SessionStorage
    private lateinit var viewModel: PaymentChatHistoryViewModel
    private lateinit var dialog: AppDialog

    private val pagedAdapter by lazy {
        PaymentChatHistoryAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_paymentChatHistoryFragment_to_homeFragment)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentChatHistoryViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        dialog = AppDialog(requireActivity())

        viewModel.userId = requireArguments().getString(KEY_USERID).toString().toInt()
        tv_balance.text = sessionStorage.balance
        viewModel.getPaymentChat(viewModel.userId)


        rv_chat.apply {
            adapter = pagedAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, true
            )
        }

        viewModel._refreshChat.value = true

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


        observe()



        iv_menu.setOnClickListener {
            showPopup(it, viewModel.userId)
        }

        btn_request.setOnClickListener {
            var bundle = bundleOf(
                KEY_USERID to viewModel.userId.toString(),
                KEY_NAME to viewModel.name,
                KEY_NUMBER to viewModel.phoneNumber,
                KEY_USERLEVEL to viewModel.userLevel.toString(),
                KEY_ROLEID to viewModel.roleId.toString(),
                KEY_PROFLE_IMAGE_LINK to viewModel.profileImage


            )
            Navigation.findNavController(view)
                .navigate(
                    R.id.action_paymentChatHistoryFragment_to_paymentRequestByContactFragment,
                    bundle
                )


        }

        btn_pay.setOnClickListener {
            var bundle = bundleOf(
                KEY_USERID to viewModel.userId.toString(),
                KEY_PAGE to SCREEN_CHAT

            )
            Navigation.findNavController(view)
                .navigate(
                    R.id.action_paymentChatHistoryFragment_to_performTransferByContactFragment,
                    bundle
                )

        }

        iv_back.setOnClickListener {
            findNavController().navigate(R.id.action_paymentChatHistoryFragment_to_homeFragment)

        }

    }

    fun observe() {
        viewModel.apply {
            paymentChatStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        cl_main.visibility = View.VISIBLE
                        name = it.data?.contact?.name
                        phoneNumber = it.data?.contact?.phoneNumber
                        userLevel = it.data?.contact?.ppp
                        roleId = it.data?.contact?.roleId
                        profileImage = it.data?.contact?.profileImage

                        if (it.data?.contact?.profileImage != null) {
                            iv_user_dpTop.visibility = View.VISIBLE
                            ll_tvHolderchat.visibility = View.INVISIBLE
                            iv_user_dpTop.loadImageWithUrl(it.data?.contact?.profileImage.toString())

                        } else {
                            iv_user_dpTop.visibility = View.INVISIBLE
                            ll_tvHolderchat.visibility = View.VISIBLE
                            tv_firstLetterrTop.text =
                                it.data?.contact?.name.toString()[0].toString()
                            tv_firstLetterrTop.setTextColor(resources.getColor(R.color.white))
                            ll_tvHolderchat.setBackgroundResource(R.drawable.greyfilled_round)
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
                        requireActivity().showShortToast(it.errorMessage)
                    }

                }
            })
            blockStatus.observe(viewLifecycleOwner, Observer {
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
                        requireActivity().showShortToast(it.errorMessage)

                    }

                }
            })

            unblockStatus.observe(viewLifecycleOwner, Observer {
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
                        requireActivity().showShortToast(it.errorMessage)

                    }

                }
            })

        }

        if(checkNetwork(requireActivity())){
            viewModel.pagedHistoryItems.observe(viewLifecycleOwner, Observer {
                pagedAdapter.submitData(lifecycle, it)
            })
        }else{
            requireActivity().showShortToast(getString(R.string.no_internet))

        }


    }

    override fun onChatRejectClicked(payment: GroupedChatHistoryResponse.Data.Row.Transaction) {
        var bundle = bundleOf(
            KEY_REF_ID to payment.transactionId,
            KEY_PAGE to SCREEN_CHAT,
            KEY_ACTION to REJECT,
            KEY_USERID to viewModel.userId.toString()

        )

        findNavController().navigate(R.id.paymentPinNumberFragment, bundle)
    }

    override fun onChatAcceptClicked(payment: GroupedChatHistoryResponse.Data.Row.Transaction) {
        var bundle = bundleOf(
            KEY_REF_ID to payment.transactionId,
            KEY_PAGE to SCREEN_CHAT,
            KEY_ACTION to ACCEPT,
            KEY_USERID to viewModel.userId.toString()

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

        if (viewModel.isBlockedContactUser == false) {
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
                    if(checkNetwork(requireActivity())) {
                        viewModel.getPaymentChat(userId.toInt())
                        refresh()

                    }else{
                        requireActivity().showShortToast(getString(R.string.no_internet))

                    }

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

        dialogBlockUnBlock.tv_name.text = viewModel.name
        dialogBlockUnBlock.tv_number.text = viewModel.phoneNumber

        if (viewModel.profileImage != null) {
            dialogBlockUnBlock.iv_user_dp.visibility = View.VISIBLE
            dialogBlockUnBlock.ll_tvHolderchatdialog.visibility = View.INVISIBLE
            dialogBlockUnBlock.iv_user_dp.loadImageWithUrl(viewModel.profileImage)

        } else {
            dialogBlockUnBlock.iv_user_dp.visibility = View.INVISIBLE
            dialogBlockUnBlock.ll_tvHolderchatdialog.visibility = View.VISIBLE
            dialogBlockUnBlock.tv_firstLetter.text = viewModel.name.toString()[0].toString()
            dialogBlockUnBlock.tv_firstLetter.setTextColor(resources.getColor(R.color.white))
            dialogBlockUnBlock.ll_tvHolderchatdialog.setBackgroundResource(R.drawable.greyfilled_round)
        }

        if (viewModel.roleId == 3) {
            if (viewModel.userLevel == 1) {
                dialogBlockUnBlock.fll_holder.setBackgroundResource(R.drawable.green_round)
            } else if (viewModel.userLevel == 2) {
                dialogBlockUnBlock.fll_holder.setBackgroundResource((R.drawable.yellow_round))
            } else if (viewModel.userLevel == 4) {
                dialogBlockUnBlock.fll_holder.setBackgroundResource(R.drawable.red_round)

            }
        } else if (viewModel.roleId == 2) {


        }

        if (viewModel.isBlockedContactUser == false) {
            dialogBlockUnBlock.tv_block.text = "Block"
            dialogBlockUnBlock.tv_content.text="Once blocked, this user will not be able to request EmCash or transfer Emcash to you"

        } else {
            dialogBlockUnBlock.tv_block.text = "Unblock"
            dialogBlockUnBlock.tv_content.text="Are you sure? Once unblocked, this user will be able to request and transfer Emcash to you"
        }

        dialogBlockUnBlock.cancel_lay.setOnClickListener {
            dialogBlockUnBlock.dismiss()
        }
        dialogBlockUnBlock.confirm_lay.setOnClickListener {
            dialogBlockUnBlock.dismiss()
            if (viewModel.isBlockedContactUser == false) {
                viewModel.blockUser(userId)

            } else {
                viewModel.unBlockUser(userId)

            }

        }


    }

    private fun refresh() {

        rv_chat.apply {
            adapter = pagedAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, true
            )
        }
        viewModel._refreshChat.value = true

    }

}

