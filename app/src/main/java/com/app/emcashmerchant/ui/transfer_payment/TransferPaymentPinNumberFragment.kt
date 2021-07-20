package com.app.emcashmerchant.ui.transfer_payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.modelrequest.TransferAmountRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.KEY_REF_ID
import com.app.emcashmerchant.utils.extensions.afterTextChanged
import com.app.emcashmerchant.utils.extensions.onDeletePressed
import com.app.emcashmerchant.utils.extensions.showKeyboard
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_transfer_payment_pin_number.*

import timber.log.Timber

class TransferPaymentPinNumberFragment : Fragment() {


    private lateinit var viewModel: TransferPaymentViewModel
    private lateinit var sessionStorage: SessionStorage
    private lateinit var dialog: AppDialog

    companion object {
        fun newInstance() = TransferPaymentPinNumberFragment()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.transferContactListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        return inflater.inflate(R.layout.fragment_transfer_payment_pin_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var qrCode = requireArguments().getString(KEY_REF_ID)
        dialog = AppDialog(requireContext())
        viewModel = ViewModelProvider(this).get(TransferPaymentViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.transferContactListFragment)

        }
        observer(view, qrCode.toString())

        handleFocusOnInput()

        et_pin_1.requestFocus()
        requireActivity().showKeyboard(et_pin_1)

        et_pin_1.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                requireActivity().showShortToast(getString(R.string.enter_valid_pin_))
                et_pin_1.text.clear()
                et_pin_2.text.clear()
                et_pin_3.text.clear()
                et_pin_4.text.clear()
                et_pin_5.text.clear()
                et_pin_6.text.clear()
            }
            false
        }
        et_pin_2.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requireActivity().showShortToast(getString(R.string.enter_valid_pin_))
                et_pin_1.text.clear()
                et_pin_2.text.clear()
                et_pin_3.text.clear()
                et_pin_4.text.clear()
                et_pin_5.text.clear()
                et_pin_6.text.clear()

            }
            false
        }
        et_pin_3.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requireActivity().showShortToast(getString(R.string.enter_valid_pin_))
                et_pin_1.text.clear()
                et_pin_2.text.clear()
                et_pin_3.text.clear()
                et_pin_4.text.clear()
                et_pin_5.text.clear()
                et_pin_6.text.clear()

            }
            false
        }
        et_pin_4.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requireActivity().showShortToast(getString(R.string.enter_valid_pin_))
                et_pin_1.text.clear()
                et_pin_2.text.clear()
                et_pin_3.text.clear()
                et_pin_4.text.clear()
                et_pin_5.text.clear()
                et_pin_6.text.clear()
            }
            false
        }
        et_pin_5.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requireActivity().showShortToast(getString(R.string.enter_valid_pin_))
                et_pin_1.text.clear()
                et_pin_2.text.clear()
                et_pin_3.text.clear()
                et_pin_4.text.clear()
                et_pin_5.text.clear()
                et_pin_6.text.clear()
            }
            false
        }
        et_pin_6.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var otp =
                    et_pin_1.text.toString() + et_pin_2.text.toString() + et_pin_3.text.toString() + et_pin_4.text.toString() + et_pin_5.text.toString()+ et_pin_6.text.toString()


                var transferAmountRequest =
                    TransferAmountRequest(
                        12.22,
                        12.22,
                        otp.toInt(),
                        qrCode.toString()
                    )
                viewModel.TransferAmount(transferAmountRequest)
            }
            false
        }


    }

    override fun onResume() {
        super.onResume()
        et_pin_1.requestFocus()
        Timber.e("onResume")
        requireActivity().showKeyboard(et_pin_1)
    }

    fun observer(view: View, qrCode: String) {
        viewModel.apply {
            transferAmountStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val bundle = bundleOf(

                            KEY_REF_ID to qrCode


                        )
                        Navigation.findNavController(view)
                            .navigate(R.id.transferpaymentRecieptFragment, bundle)

                    }
                    ApiCallStatus.ERROR -> {
                        et_pin_1.text.clear()
                        et_pin_2.text.clear()
                        et_pin_3.text.clear()
                        et_pin_4.text.clear()
                        et_pin_5.text.clear()
                        et_pin_6.text.clear()

                        et_pin_1.setBackgroundResource(R.drawable.circle_black_outlined)
                        et_pin_2.setBackgroundResource(R.drawable.circle_black_outlined)
                        et_pin_3.setBackgroundResource(R.drawable.circle_black_outlined)
                        et_pin_4.setBackgroundResource(R.drawable.circle_black_outlined)
                        et_pin_5.setBackgroundResource(R.drawable.circle_black_outlined)
                        et_pin_6.setBackgroundResource(R.drawable.circle_black_outlined)

                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(it.errorMessage)

                    }
                }
            })

        }
    }

    private fun handleFocusOnInput() {



        et_pin_1.requestFocus()
        et_pin_1.afterTextChanged {
            if (it.length == 1) {
                et_pin_2.requestFocus()
                et_pin_1.setBackgroundResource(R.drawable.circle_black_fill)
            } else if (it.length == 2) {
                et_pin_2.setText(it.last().toString())
                et_pin_2.setBackgroundResource(R.drawable.circle_black_fill)

                et_pin_1.setText(it.first().toString())
                et_pin_2.requestFocus()
                et_pin_2.setSelection(1)
                et_pin_1.setBackgroundResource(R.drawable.circle_black_fill)


            }
        }
        et_pin_2.afterTextChanged {
            if (it.length == 1) {
                et_pin_3.requestFocus()
                et_pin_2.setBackgroundResource(R.drawable.circle_black_fill)

            } else if (it.length == 2) {
                et_pin_3.setText(it.last().toString())
                et_pin_3.setBackgroundResource(R.drawable.circle_black_fill)

                et_pin_2.setText(it.first().toString())
                et_pin_3.requestFocus()
                et_pin_3.setSelection(1)
                et_pin_2.setBackgroundResource(R.drawable.circle_black_fill)


            }
        }
        et_pin_3.afterTextChanged {
            if (it.length == 1) {
                et_pin_4.requestFocus()
                et_pin_3.setBackgroundResource(R.drawable.circle_black_fill)

            } else if (it.length == 2) {
                et_pin_4.setText(it.last().toString())
                et_pin_4.setBackgroundResource(R.drawable.circle_black_fill)

                et_pin_3.setText(it.first().toString())
                et_pin_4.requestFocus()
                et_pin_4.setSelection(1)
//                validate()
                et_pin_3.setBackgroundResource(R.drawable.circle_black_fill)


            }
        }
        et_pin_4.afterTextChanged {
            if (it.length == 1) {
                et_pin_5.requestFocus()
                et_pin_4.setBackgroundResource(R.drawable.circle_black_fill)

            } else if (it.length == 2) {
                et_pin_5.setText(it.last().toString())
                et_pin_5.setBackgroundResource(R.drawable.circle_black_fill)

                et_pin_4.setText(it.first().toString())
                et_pin_5.requestFocus()
                et_pin_5.setSelection(1)
//                validate()
                et_pin_4.setBackgroundResource(R.drawable.circle_black_fill)


            }
        }
        et_pin_5.afterTextChanged {
            if (it.length == 1) {
                et_pin_6.requestFocus()
                et_pin_5.setBackgroundResource(R.drawable.circle_black_fill)

            } else if (it.length == 2) {
                et_pin_6.setText(it.last().toString())
                et_pin_6.setBackgroundResource(R.drawable.circle_black_fill)

                et_pin_5.setText(it.first().toString())
                et_pin_6.requestFocus()
                et_pin_6.setSelection(1)
//                validate()
                et_pin_5.setBackgroundResource(R.drawable.circle_black_fill)


            }
        }

        et_pin_6.afterTextChanged {
            et_pin_6.setBackgroundResource(R.drawable.circle_black_fill)
//            validate()
        }


        et_pin_1.onDeletePressed {
            et_pin_1.text.clear()
            et_pin_1.setBackgroundResource(R.drawable.circle_black_outlined)

            et_pin_1.requestFocus()
            et_pin_1.setSelection(0)
        }
        et_pin_2.onDeletePressed {
            et_pin_2.text.clear()
            et_pin_2.setBackgroundResource(R.drawable.circle_black_outlined)

            et_pin_1.requestFocus()
            et_pin_1.setSelection(et_pin_1.text.lastIndex.plus(1))
        }
        et_pin_3.onDeletePressed {
            et_pin_3.text.clear()
            et_pin_3.setBackgroundResource(R.drawable.circle_black_outlined)

            et_pin_2.requestFocus()
            et_pin_2.setSelection(et_pin_2.text.lastIndex.plus(1))
        }


        et_pin_4.onDeletePressed {

            et_pin_4.text.clear()
            et_pin_4.setBackgroundResource(R.drawable.circle_black_outlined)

            et_pin_3.requestFocus()
            et_pin_3.setSelection(et_pin_3.text.lastIndex.plus(1))

        }

        et_pin_5.onDeletePressed {

            et_pin_5.text.clear()
            et_pin_5.setBackgroundResource(R.drawable.circle_black_outlined)

            et_pin_4.requestFocus()
            et_pin_4.setSelection(et_pin_3.text.lastIndex.plus(1))

        }

        et_pin_6.onDeletePressed {

            et_pin_6.text.clear()
            et_pin_6.setBackgroundResource(R.drawable.circle_black_outlined)

            et_pin_5.requestFocus()
            et_pin_5.setSelection(et_pin_3.text.lastIndex.plus(1))

        }


    }
}