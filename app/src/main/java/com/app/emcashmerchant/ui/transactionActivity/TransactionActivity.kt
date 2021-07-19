package com.app.emcashmerchant.ui.transactionActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.TopUpRequest
import com.app.emcashmerchant.data.models.CardResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.ui.loadEmcash.LoadEmcashViewModel
import com.app.emcashmerchant.ui.transactionActivity.adapter.CardsAdapter
import com.app.emcashmerchant.ui.wallet.WalletFragment
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {
    private lateinit var viewModel: LoadEmcashViewModel
    lateinit var dialog: AppDialog

    private val amount by lazy {
        intent.getIntExtra(KEY_AMOUNT, 0)
    }
    private val description by lazy {
        intent.getStringExtra(KEY_DESCRIPTION)
    }
    private val latitude by lazy {
        intent.getDoubleExtra(KEY_LATITUDE, 0.0)
    }
    private val longitude by lazy {
        intent.getDoubleExtra(KEY_LONGITUDE, 0.0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        dialog = AppDialog(this)

        getData()
        getViewModel()
        observe()

    }

    private fun topUp() {

        val topUpRequest = TopUpRequest(amount, description.toString(), latitude, longitude)
        viewModel.topUp(topUpRequest)

    }

    private fun observe() {
        viewModel.apply {
            topupStatus.observe(this@TransactionActivity, androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        showShortToast(getString(R.string.emcash_loaded))
                        openActivity(HomeBaseActivity::class.java)


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)

                    }
                }

            })

        }
    }

    private fun getViewModel() {
        viewModel = obtainViewModel(LoadEmcashViewModel::class.java)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tab_empay -> {
                tab_empay.setBackgroundResource(R.drawable.blue_stroke_light_blue_fill_round_bg)
                tab_bank_card.setBackgroundResource(R.drawable.grey_rounded_bg)

                iv_empay_selected.isVisible = true
                iv_bank_selected.isVisible = false
            }
            R.id.tab_bank_card -> {
                tab_bank_card.setBackgroundResource(R.drawable.blue_stroke_light_blue_fill_round_bg)
                tab_empay.setBackgroundResource(R.drawable.grey_rounded_bg)
                iv_bank_selected.isVisible = true
                iv_empay_selected.isVisible = false

            }
            R.id.btn_continue -> {
                topUp()
            }
        }
    }

    private fun getData() {
        val cards = ArrayList<CardResponse>()

        cards.add(CardResponse(1,"Empay - Prepaid- XXXXX 145", "AED 234.20", true))
        cards.add(CardResponse(2,"Empay - Credit- XXXXX 607", "AED 234.20", false))

        rv_cards.apply {
            layoutManager = LinearLayoutManager(
                this@TransactionActivity,
                RecyclerView.VERTICAL, false
            )
            itemAnimator = DefaultItemAnimator()
            adapter = CardsAdapter(cards)
        }
    }


}