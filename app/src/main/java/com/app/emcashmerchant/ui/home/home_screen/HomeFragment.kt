package com.app.emcashmerchant.ui.home.home_screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.home.home_screen.adapter.RecentPaymentsAdapter
import kotlinx.android.synthetic.main.home_fragment.*
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
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        //test setup badge
        iv_notification_badge_view.setupBadge(3333)

        iv_shop_profile_image.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_settings_fragment)
        }

        setPaymentList()
    }

    private fun setPaymentList() {
        rv_recent_payment_list.apply {
            layoutManager = GridLayoutManager(requireContext(),5)
            adapter = RecentPaymentsAdapter()
        }
    }


}