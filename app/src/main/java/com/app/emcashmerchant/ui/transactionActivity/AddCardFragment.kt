package com.app.emcashmerchant.ui.transactionActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.PaymentByNewCardRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.loadEmcash.LoadEmcashViewModel
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : Fragment() {

    private lateinit var viewModel: LoadEmcashViewModel
    lateinit var dialog: AppDialog
    var saveAfterTransaction:Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return inflater.inflate(R.layout.fragment_add_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoadEmcashViewModel::class.java)
        dialog = AppDialog(requireActivity())


        var description = requireArguments().getString(KEY_DESCRIPTION)
        var latitude = requireArguments().getDouble(KEY_LATITUDE)
        var longitude = requireArguments().getDouble(KEY_LONGITUDE)
        var amount = requireArguments().getInt(KEY_AMOUNT)


        observer()
        iv_back.setOnClickListener {
            findNavController().popBackStack()

        }
        cb_saveCard.setOnCheckedChangeListener { buttonView, isChecked ->
            saveAfterTransaction=true
        }

        btn_continue.setOnClickListener {
            var cvv = et_cvv.text.toString()
            var expiryDate = et_expDate.text.toString()
            var cardHolderName = et_CardHolderName.text.toString()
            var atmNumber = et_atmNumber.text.toString()


            if (expiryDate.isEmpty() || cvv.isEmpty() || cardHolderName.isEmpty()) {
                requireActivity().showShortToast("Please enter all fields")
            } else {
                var billerId = "9000001"
                var customer = PaymentByNewCardRequest.Customer("540000010", 1)
                var amountAuthorized =
                    PaymentByNewCardRequest.AmountAuthorized("AED", String.format("%.2f", amount.toDouble()) )
                var card = PaymentByNewCardRequest.Card(
                    cvv,
                    "manual",
                    expiryDate,
                    cardHolderName,
                    atmNumber, saveAfterTransaction
                )

                var paymentByNewCardRequest =
                    PaymentByNewCardRequest(
                        amountAuthorized,
                        billerId,
                        card,
                        customer,
                        description,
                        null,
                        "07",
                        latitude,
                        true,
                        longitude,
                        false
                    )
                viewModel.paymentByNewCard(paymentByNewCardRequest)
            }


        }
    }

    private fun observer() {
        viewModel.apply {
            paymentByNewCardStatus.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(getString(R.string.emcash_loaded))
                        findNavController().navigate(R.id.walletFragment)


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