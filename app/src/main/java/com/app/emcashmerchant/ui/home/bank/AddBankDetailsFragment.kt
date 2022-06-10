package com.app.emcashmerchant.ui.home.bank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.AddBankDetailsRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.setInputFilter
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_add_bank_details.*

/**
 Fragment to add the bank details
 **/
class AddBankDetailsFragment : Fragment() {


    private lateinit var viewModel: BankViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            findNavController().popBackStack()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bank_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BankViewModel::class.java)
        sessionStorage = SessionStorage(requireContext())
        dialog = AppDialog(requireContext())
        observe()
        setInputFiltersForEditText()

        iv_back.setOnClickListener{
            findNavController().popBackStack()

        }

        bt_submit.setOnClickListener {
            var benficiaryName: String = et_benficiaryName.text.toString()
            var nickName: String = et_nickName.text.toString()
            var ibanNumber: String = et_ibanNumber.text.toString()
            var branchName: String = et_branchName.text.toString()
            var branchCode: String = et_branchCode.text.toString()
            var swiftCode: String = et_swiftCode.text.toString()

            if ( benficiaryName.isBlank()
                || ibanNumber.isBlank()
                || branchName.isBlank()
                || branchCode.isBlank()
                || swiftCode.isBlank()) {
                requireActivity().showShortToast("Please enter all the fields")

            }else{
                var addBankDetailsRequest = AddBankDetailsRequest(
                    benficiaryName,
                    branchName,
                    ibanNumber,
                    nickName,
                    swiftCode,branchCode
                )
                viewModel.addBankDetails(addBankDetailsRequest)
            }

        }


    }

    private fun setInputFiltersForEditText(){
        et_benficiaryName.setInputFilter()
        et_nickName.setInputFilter()
        et_ibanNumber.setInputFilter()
        et_branchName.setInputFilter()
        et_branchCode.setInputFilter()
        et_swiftCode.setInputFilter()
    }
    fun observe() {
        viewModel.apply {
            addBankDetailsStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.data?.message)
                        findNavController().popBackStack()

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)
                    }
                }
            })
        }

    }


}