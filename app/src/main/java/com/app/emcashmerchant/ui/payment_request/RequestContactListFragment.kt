package com.app.emcashmerchant.ui.payment_request

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.PaymentRequest
import com.app.emcashmerchant.data.models.AllContactResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.payment_request.adapter.AllContactsRequestAdapter
import com.app.emcashmerchant.ui.payment_request.adapter.ContactsItemClickListener
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.KEY_AMOUNT
import com.app.emcashmerchant.utils.KEY_DESCRIPTION
import kotlinx.android.synthetic.main.dialog_confirmation.*
import kotlinx.android.synthetic.main.fragment_request_contact_list.*


class RequestContactListFragment : Fragment(), ContactsItemClickListener {
    private lateinit var viewModel: PaymentRequestViewModel
    var amount:String = ""
    var description:String?=null
    lateinit var dialog: AppDialog
    lateinit var dialogConfirmation: Dialog
    companion object {
        fun newInstance() = RequestContactListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)

        return inflater.inflate(R.layout.fragment_request_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog= AppDialog(requireContext())
        viewModel = ViewModelProvider(this).get(PaymentRequestViewModel::class.java)
        viewModel.getContactsList("")
         amount = requireArguments().getString(KEY_AMOUNT).toString()
         description = requireArguments().getString(KEY_DESCRIPTION).toString()
        observe(view)

        iv_back.setOnClickListener {
            findNavController().popBackStack()

        }

        sv_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getContactsList(sv_search.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

    }


    fun observe(view: View) {
        viewModel.apply {
            allContactsStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it.data?.rows?.let {
                            rv_contactList.apply {

                                layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    RecyclerView.VERTICAL,
                                    false
                                )
                                adapter = AllContactsRequestAdapter(it,this@RequestContactListFragment)

                            }
                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()

                    }

                }
            })

            paymentRequestStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        findNavController().navigate(R.id.action_requestContactListFragment_to_paymentChatHistoryFragment)


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()

                    }

                }
            })
        }
    }

    override fun onContactClicked(contact: AllContactResponse.Data.Row) {
        dialogConfirmation(contact)
    }
    private fun dialogConfirmation(contact: AllContactResponse.Data.Row) {
        dialogConfirmation = Dialog(requireActivity())
        dialogConfirmation.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogConfirmation.setContentView(R.layout.dialog_confirmation)
        dialogConfirmation.setCancelable(false)
        dialogConfirmation.setCanceledOnTouchOutside(false)
        dialogConfirmation.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogConfirmation.show()
        dialogConfirmation.btn_okay.setOnClickListener {
            dialogConfirmation.dismiss()
            val paymentRequest=PaymentRequest(amount.toInt(),description.toString(),contact.id.toInt())
            viewModel.paymentRequest(paymentRequest)

        }
        dialogConfirmation.btn_cancel.setOnClickListener {
            dialogConfirmation.dismiss()

        }
    }
}