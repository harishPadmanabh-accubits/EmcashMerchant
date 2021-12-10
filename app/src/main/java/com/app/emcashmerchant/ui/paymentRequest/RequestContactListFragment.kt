package com.app.emcashmerchant.ui.paymentRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.request.PaymentRequest
import com.app.emcashmerchant.data.model.response.GroupedContactsResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.paymentRequest.adapter.AllContactsRequestsAdapter
import com.app.emcashmerchant.ui.paymentRequest.adapter.ContactsItemClickListener
import com.app.emcashmerchant.ui.paymentRequest.viewModel.PaymentRequestViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.KEY_AMOUNT
import com.app.emcashmerchant.utils.KEY_DESCRIPTION
import com.app.emcashmerchant.utils.KEY_USERID
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.fragment_perform_transfer_payment.*
import kotlinx.android.synthetic.main.fragment_request_contact_list.*
import kotlinx.android.synthetic.main.fragment_request_contact_list.iv_back
import kotlinx.android.synthetic.main.fragment_transfer_contact_list.*
import kotlinx.android.synthetic.main.lay_confirmation.*
import kotlinx.android.synthetic.main.lay_confirmation.tv_name
import kotlinx.android.synthetic.main.lay_confirmation.tv_number
import kotlinx.android.synthetic.main.lay_confirmation.tv_total_bill_amount
import timber.log.Timber


class RequestContactListFragment : Fragment(R.layout.fragment_request_contact_list), ContactsItemClickListener {
    private lateinit var viewModel: PaymentRequestViewModel
    var amount: String = ""
    var description: String? = null
    var userId: String = ""
    lateinit var dialog: AppDialog
    val pagedAdapter by lazy {
        AllContactsRequestsAdapter(this)
    }
    companion object {
        fun newInstance() = RequestContactListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.paymentRequestFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = AppDialog(requireContext())
        viewModel = ViewModelProvider(this).get(PaymentRequestViewModel::class.java)
        viewModel.getContactsList("")
        amount = requireArguments().getString(KEY_AMOUNT).toString()
        description = requireArguments().getString(KEY_DESCRIPTION).toString()





        sv_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search.postValue(sv_search.text.toString())
                Timber.e("searched")
                return@OnEditorActionListener true
            }
            false
        })

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

        iv_back.setOnClickListener {
            findNavController().navigate(R.id.paymentRequestFragment)

        }

        rv_contactList.apply {
            adapter = pagedAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
        }


        viewModel.pagedContacts.observe(viewLifecycleOwner, Observer {
            pagedAdapter.submitData(lifecycle,it)
        })


        observe(view)

    }


    fun observe(view: View) {
        viewModel.apply {
            paymentRequestStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        var bundle = bundleOf(
                            KEY_USERID to userId
                        )
                        findNavController().navigate(
                            R.id.action_requestContactListFragment_to_paymentChatHistoryFragment,
                            bundle
                        )


                    }
                    ApiCallStatus.ERROR -> {
                        requireActivity().showShortToast(it.errorMessage)
                        dialog.dismiss_dialog()

                    }

                }
            })
        }
    }

    override fun onContactClicked(contact: GroupedContactsResponse.Data.Row.Contact) {
        dialogConfirmation(contact)
    }

    private fun dialogConfirmation(contact: GroupedContactsResponse.Data.Row.Contact) {
        fl_confirmation.visibility = View.VISIBLE
        var profileImage=contact.profileImage

        if (profileImage != null) {
            iv_user_dp.loadImageWithUrl(profileImage.toString())
            iv_user_dp.visibility = View.VISIBLE
            ll_tvHolderconfirmation.visibility = View.GONE


        } else {
            iv_user_dp.visibility = View.GONE
            ll_tvHolderconfirmation.visibility = View.VISIBLE
            tv_firstLetterr_confirmation.text = contact.name[0].toString()
            ll_tvHolderconfirmation.setBackgroundResource(R.drawable.greyfilled_round)
            tv_firstLetterr_confirmation.setTextColor(resources.getColor(R.color.white))
        }

        var roleId=contact.roleId
        var userLevel=contact.ppp
        if (roleId == 3) {
            if (userLevel == 1) {
                fl_user_level_confirmation.setBackgroundResource(R.drawable.green_round)
            } else if (userLevel == 2) {
                fl_user_level_confirmation.setBackgroundResource((R.drawable.yellow_round))
            } else if (userLevel == 4) {
                fl_user_level_confirmation.setBackgroundResource(R.drawable.red_round)
            }
        }else{
            fl_user_level_confirmation.setBackgroundResource(0)

        }

        iv_close.setOnClickListener {
            fl_confirmation.visibility = View.GONE

        }
        tv_name.text = contact.name
        tv_number.text = contact.phoneNumber
        tv_total_bill_amount.text = "Total Bill amount of $amount AED"

        btn_request.setOnClickListener {
            fl_confirmation.visibility = View.GONE

            userId = contact.id
            val paymentRequest =
                PaymentRequest(amount.toInt(), description.toString(), contact.id.toInt())
            viewModel.paymentRequest(paymentRequest)
        }

    }


}