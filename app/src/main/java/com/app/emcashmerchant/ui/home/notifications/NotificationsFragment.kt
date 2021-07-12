package com.app.emcashmerchant.ui.home.notifications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.dummyNotificaitonData
import com.app.emcashmerchant.ui.home.notifications.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.notifications_fragment.*

class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() =
            NotificationsFragment()
    }

    private lateinit var viewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return inflater.inflate(R.layout.notifications_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }

        rv_notification.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
            adapter = NotificationAdapter(dummyNotificaitonData)
        }
    }


}