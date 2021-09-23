package com.app.emcashmerchant.ui.home.home_screen

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.ChangeBounds
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.home.home_screen.adapter.RecentTransactionsAdapter
import com.app.emcashmerchant.ui.loadEmcash.LoadEmcashViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.loadImageWithUrlUser
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.utils.extensions.trimID
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.layout_home_info_card.*
import kotlinx.android.synthetic.main.layout_notification_with_badge_view.*
import timber.log.Timber

/**
 Home Screen
 **/
class HomeFragment : Fragment(R.layout.home_fragment) {

    private  lateinit var viewModel: HomeViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog

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
        sessionStorage = SessionStorage(requireContext())
        val deepLink = sessionStorage.pendingDeeplink
        if(!deepLink.isNullOrEmpty()){
            Timber.e("Deeplink in home $deepLink")
            sessionStorage.pendingDeeplink = null
            findNavController().navigate(Uri.parse(deepLink))
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        dialog = AppDialog(requireActivity())

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
        //test setup badge

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
            Navigation.findNavController(view).navigate(R.id.goto_transactions_history_fragment)
        }

        cv_home_info.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_wallet_fragment)
        }

        tv_convert_to_cash.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.goto_convert_cash)
        }

        iv_notification_badge_view.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_notifications_fragment_from_home)
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
        viewModel.getWalletDetails()
        tv_num_emcash.setText(sessionStorage.balance)
        viewModel.getRecentTransactions()

    }

    private fun observe() {
        viewModel.apply {
            walletDetails.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it?.data?.let {
                            var notificationCounts = it.notificationCount?.toInt()
                            iv_shop_profile_image.loadImageWithUrlUser(it.profileImage)
                            if (notificationCounts.toString().toInt() >= 10) {
                                notificationCount.text = "9+"
                            } else {
                                notificationCount.text = notificationCounts.toString()

                            }
                            balance = it.amount.toString()
                            if (balance != null) {
                                sessionStorage.balance = balance
                                tv_num_emcash.setText(sessionStorage.balance)

                            }
                            1
                            tv_wallet_id.text = "Wallet ID : ".plus(trimID(it.id.toString()))

                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)
                    }
                }
            })

            recentTransactions.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it.data?.rows?.let {
                            var recentTransactions = it

                            if (recentTransactions.isNotEmpty()) {
                                rv_recent_payment.visibility = View.VISIBLE
                                ll_no_transactions.visibility = View.GONE

                                rv_recent_payment.apply {
                                    layoutManager = GridLayoutManager(requireContext(), 5)
                                    adapter = RecentTransactionsAdapter(recentTransactions)
                                }
                            } else {
                                rv_recent_payment.visibility = View.GONE
                                ll_no_transactions.visibility = View.VISIBLE
                            }
                        }


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
