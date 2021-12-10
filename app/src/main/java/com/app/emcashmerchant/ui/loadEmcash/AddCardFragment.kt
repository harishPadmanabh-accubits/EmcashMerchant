package com.app.emcashmerchant.ui.loadEmcash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.model.request.PaymentByNewCardRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.loadEmcash.viewModel.LoadEmcashViewModel
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.emcash.customerapp.utils.BankCardExpiryDateFormatter
import com.emcash.customerapp.utils.BankCardNumberFormatter
import kotlinx.android.synthetic.main.fragment_add_card.*
import timber.log.Timber

class AddCardFragment : Fragment(R.layout.fragment_add_card) {

    private lateinit var viewModel: LoadEmcashViewModel
    lateinit var dialog: AppDialog
    var saveAfterTransaction:Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this){
//            findNavController().navigate(R.id.loadEmcashFragment)
            findNavController().popBackStack()

        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoadEmcashViewModel::class.java)
        dialog = AppDialog(requireActivity())


         viewModel.description = requireArguments().getString(KEY_DESCRIPTION)
         viewModel.latitude = requireArguments().getDouble(KEY_LATITUDE)
        viewModel.longitude = requireArguments().getDouble(KEY_LONGITUDE)
         viewModel.amount = requireArguments().getString(KEY_AMOUNT)


        observer()
        Timber.d("latitudeloademcash ${viewModel.latitude}")
        Timber.d("longitudeloademcash ${viewModel.longitude}")


        iv_back.setOnClickListener {
//            findNavController().navigate(R.id.loadEmcashFragment)
            findNavController().popBackStack()

        }
        cb_saveCard.setOnCheckedChangeListener { buttonView, isChecked ->
            saveAfterTransaction=true
        }

        et_atmNumber.addTextChangedListener(BankCardNumberFormatter())
        et_expDate.apply {
            addTextChangedListener(BankCardExpiryDateFormatter(this))
        }


        btn_continue.setOnClickListener {
            var cvv = et_cvv.text.toString()
            var expiryDate = et_expDate.text.toString().replace("/","")
            var cardHolderName = et_CardHolderName.text.toString()
            var atmNumber = et_atmNumber.text.toString().filter{!it.isWhitespace()}


            if (expiryDate.isEmpty() || cvv.isEmpty() || cardHolderName.isEmpty()) {
                requireActivity().showShortToast("Please enter all fields")
            } else {
                var billerId = "9000001"
                var customer = PaymentByNewCardRequest.Customer("540000010", 1)
                var amountAuthorized =
                    PaymentByNewCardRequest.AmountAuthorized("AED", String.format("%.2f", viewModel.amount?.toDouble()) )
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
                        viewModel.description,
                        null,
                        "07",
                        viewModel.latitude,
                        true,
                        viewModel.longitude,
                        false
                    )
                viewModel.paymentByNewCard(paymentByNewCardRequest)
            }


        }
    }

    private fun observer() {
        viewModel.apply {
            paymentByNewCardStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        if(it.data?.data?.decision.equals("PAYER_AUTH_REQUIRED")){
                            val bundle = bundleOf(
                                KEY_URL3D to it.data?.data?.payerAuthentication?.url3D,
                                KEY_ORDERID to  it.data?.data?.orderId,
                                KEY_SESSIONID to it.data?.data?.sessionId,
                                KEY_AMOUNT to amount.toString(),
                                KEY_DESCRIPTION to  description,
                                KEY_LATITUDE to latitude,
                                KEY_LONGITUDE to longitude
                            )
                            findNavController().navigate(R.id.action_addCardFragment_to_emPayWebViewFragment,bundle)
                        }else{
                            requireActivity().showShortToast(getString(R.string.emcash_loaded))
                            findNavController().navigate(R.id.walletFragment)
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