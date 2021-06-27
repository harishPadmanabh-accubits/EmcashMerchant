package com.app.emcashmerchant.ui.home.home_screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.home.home_screen.adapter.RecentPaymentsAdapter
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.layout_home_info_card.*
import timber.log.Timber

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() =
            HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("onViewCreated")
        initViewModel(requireActivity())
        setupViews(view)
    }

    private fun setupViews(view: View) {
       //test setup badge
        iv_notification_badge_view.setupBadge(3333)

        iv_shop_profile_image.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_settings_fragment)
        }

        tv_info_history.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_transactions_history_fragment)
        }

        tv_wallet_id.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_wallet_fragment)
        }

        tv_convert_to_cash.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_convert_cash)
        }

        iv_notification_badge_view.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_notifications_fragment_from_home)
        }

        tv_transfer_payment.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_transfer_payment_activity)
        }

        setPaymentList()

    }

    private fun initViewModel(fragmentActivity: FragmentActivity?) {
        if (fragmentActivity != null)
            fragmentActivity.obtainViewModel(HomeViewModel::class.java)
        else
            Timber.e("ViewModel init failed")
    }

    private fun setPaymentList() {
        rv_recent_payment_list.apply {
            layoutManager = GridLayoutManager(requireContext(), 5)
            adapter = RecentPaymentsAdapter()
        }
    }


}