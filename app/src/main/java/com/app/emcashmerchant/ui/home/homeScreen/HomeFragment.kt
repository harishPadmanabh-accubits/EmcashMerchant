package com.app.emcashmerchant.ui.home.homeScreen

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.ChangeBounds
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.home.homeScreen.adapter.RecentTransactionsAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.KEY_PAGE
import com.app.emcashmerchant.utils.SCREEN_HOME
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.layout_home_info_card.*
import kotlinx.android.synthetic.main.layout_notification_with_badge_view.*
import kotlinx.coroutines.async

/**
Home Screen
 **/
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private val sessionStorage by lazy { SessionStorage(requireActivity()) }
    private val dialog by lazy { AppDialog(requireActivity()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
        Method to handle deepLinks
         **/
        handlePendingDeepLink()


        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 750
        }

        /**
        Backpress Callback
         **/
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ActivityCompat.finishAffinity(requireActivity())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

    }

    private fun handlePendingDeepLink() {
        val deepLink = sessionStorage.pendingDeeplink
        if (!deepLink.isNullOrEmpty()) {
            sessionStorage.pendingDeeplink = null
            findNavController().navigate(Uri.parse(deepLink))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /**
        Handling Views and navigations
         **/
        setupViews(view)

        /**
        calling viewModels
         **/
        viewModels()

        /**
        method to observe the liveData
         **/
        observe()

    }

    private fun setupViews(view: View) {

        iv_shop_profile_image.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                it to "shop_image_transition"
            )
            Navigation.findNavController(view)
                .navigate(R.id.goto_settings_fragment, null, null, extras)
        }
        tv_wallet_id.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.walletFragment, null, null)

        }

        tv_convert_to_cash.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.convertToCashFragment, null, null)

        }

        tv_info_history.setOnClickListener {
            if (checkNetwork(requireActivity())) {
                Navigation.findNavController(view).navigate(R.id.goto_transactions_history_fragment)

            } else {
                requireActivity().showShortToast(getString(R.string.no_internet))

            }
        }

        cv_home_info.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_wallet_fragment)
        }

        tv_convert_to_cash.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.goto_convert_cash)
        }

        iv_notification_badge_view.setOnClickListener {
            val bundle = bundleOf(
                KEY_PAGE to SCREEN_HOME

            )

            Navigation.findNavController(view)
                .navigate(R.id.goto_notifications_fragment_from_home, bundle)
        }

        tv_transfer_payment.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.transferContactListFragment)
        }
        fab_new_payment.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.paymentRequestFragment)

        }

        ll_no_transactions.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.transferContactListFragment)

        }
    }


    private fun viewModels() {

        viewModel.getRecentTransactions()
        viewModel.getWalletDetails()
        tv_num_emcash.text = sessionStorage.balance

    }

    private fun observe() {
        viewModel.apply {
            walletDetails.observe(viewLifecycleOwner, Observer { it ->
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it?.data?.let {
                            val notificationCounts = it.notificationCount
                            iv_shop_profile_image.loadImageWithErrorCallback(it.profileImage, onError = {
                                tv_user_name_letter.text = generateDisplayPicText(sessionStorage.merchantName) }
                            )
                            sessionStorage.profileImage = it.profileImage

                            if (notificationCounts.toString().toInt() >= 10) {
                                notificationCount.text = "9+"
                            } else {
                                notificationCount.text = notificationCounts.toString()

                            }
                            balance = it.amount.toString()
                            if (balance != null) {
                                sessionStorage.balance = balance
                                tv_num_emcash.text = sessionStorage.balance

                            }

                            tv_wallet_id.text = getString(R.string.walletId) .plus(" ${trimID(it.id.toString())}")

                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)
                    }
                }
            })

            recentTransactions.observe(viewLifecycleOwner, Observer { it ->
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it.data?.rows?.let { transactions ->

                            if (transactions.isNotEmpty()) {
                                rv_recent_payment.visibility = View.VISIBLE
                                ll_no_transactions.visibility = View.GONE

                                rv_recent_payment.apply {
                                    layoutManager = GridLayoutManager(requireContext(), 5)
                                    adapter = RecentTransactionsAdapter(transactions)
                                }
                            } else {
                                rv_recent_payment.visibility = View.GONE
                                ll_no_transactions.visibility = View.VISIBLE
                            }
                        }


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                    }
                }
            })


        }
    }


}
