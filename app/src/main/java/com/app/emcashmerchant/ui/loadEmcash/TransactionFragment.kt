package com.app.emcashmerchant.ui.loadEmcash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.PaymentByExisitingCardRequest
import com.app.emcashmerchant.data.models.BankCardsListingResponse
import com.app.emcashmerchant.data.models.CardResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.loadEmcash.adapter.CardsAdapter
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.android.synthetic.main.fragment_transaction.btn_continue
import kotlinx.android.synthetic.main.fragment_transaction.iv_back
import kotlinx.android.synthetic.main.fragment_transaction.iv_bank_selected
import kotlinx.android.synthetic.main.fragment_transaction.iv_empay_selected
import kotlinx.android.synthetic.main.fragment_transaction.tab_bank_card
import kotlinx.android.synthetic.main.fragment_transaction.tab_empay
import kotlinx.android.synthetic.main.fragment_transaction.tv_info_currency

class TransactionFragment : Fragment(R.layout.fragment_transaction), CardsAdapter.CardsItemClickListener {
    private lateinit var viewModel: LoadEmcashViewModel
    lateinit var dialog: AppDialog
    var useExistingCard: String?=null

    companion object {
        fun newInstance() =
            TransactionFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(this){
            //                findNavController().navigate(R.id.loadEmcashFragment)

            findNavController().popBackStack()

        }



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoadEmcashViewModel::class.java)
        dialog = AppDialog(requireActivity())
        var amount = requireArguments().getInt(KEY_AMOUNT)

        observe(view)
        getData()
        performAction(view, amount.toDouble())
        viewModel.bankCardListing()
        tv_info_currency.text = amount.toString()

    }

    fun performAction(view: View, amount: Double) {

        rv_Empaycards.isVisible = true
        ll_bankCards.isVisible = false

        viewModel.description = requireArguments().getString(KEY_DESCRIPTION)
        viewModel.latitude = requireArguments().getDouble(KEY_LATITUDE)
        viewModel.longitude = requireArguments().getDouble(KEY_LONGITUDE)

        cl_addCard.setOnClickListener {
            val bundle = bundleOf(
                KEY_AMOUNT to amount.toString(),
                KEY_DESCRIPTION to viewModel.description,
                KEY_LATITUDE to viewModel.latitude,
                KEY_LONGITUDE to viewModel.longitude
            )

            findNavController().navigate(R.id.addCardFragment, bundle)

        }


        tab_empay.setOnClickListener {
            tab_empay.setBackgroundResource(R.drawable.blue_stroke_light_blue_fill_round_bg)
            tab_bank_card.setBackgroundResource(R.drawable.grey_rounded_bg)

            rv_Empaycards.isVisible = true
            ll_bankCards.isVisible = false
            iv_empay_selected.isVisible = true
            iv_bank_selected.isVisible = false
        }
        tab_bank_card.setOnClickListener {

            rv_Empaycards.isVisible = false
            ll_bankCards.isVisible = true
            tab_bank_card.setBackgroundResource(R.drawable.blue_stroke_light_blue_fill_round_bg)
            tab_empay.setBackgroundResource(R.drawable.grey_rounded_bg)
            iv_bank_selected.isVisible = true
            iv_empay_selected.isVisible = false

        }
        iv_back.setOnClickListener {
//            findNavController().navigate(R.id.loadEmcashFragment)
            findNavController().popBackStack()

        }

        btn_continue.setOnClickListener {

            var customer = PaymentByExisitingCardRequest.Customer("540000010", 1)
            var amount = PaymentByExisitingCardRequest.Amount("AED",  String.format("%.2f", amount))
            var paymentByExisitingCardRequest =
                PaymentByExisitingCardRequest(
                    amount,
                    "9000001",
                    null,
                    customer,
                    viewModel.description,
                    useExistingCard.toString(), "07", viewModel.latitude, true, viewModel.longitude, true
                )

            viewModel.paymentByExistingCard(paymentByExisitingCardRequest)

        }

    }

    private fun observe(view: View) {
        viewModel.apply {
            paymentByExistingCardStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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
            bankCardStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        rv_bankCard.apply {
                            adapter = it.data?.let { it1 ->
                                CardsAdapter(
                                    it1.cards,
                                    this@TransactionFragment
                                )
                            }
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

    private fun getData() {
        val cards = ArrayList<CardResponse>()

        cards.add(CardResponse(1, "Empay - Prepaid- XXXXX 145", "AED 234.20", true))
        cards.add(CardResponse(2, "Empay - Credit- XXXXX 607", "AED 234.20", false))

    }

    override fun onCardClicked(card: BankCardsListingResponse.Data.Card) {
        useExistingCard = card.token

    }
}