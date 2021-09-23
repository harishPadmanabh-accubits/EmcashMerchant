package com.app.emcashmerchant.ui.home.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.home.notifications.adapter.NotificationAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.checkNetwork
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.notifications_fragment.*
import kotlinx.android.synthetic.main.notifications_fragment.iv_back
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
Fragment to display the notification
 **/
class NotificationsFragment : Fragment(R.layout.notifications_fragment) {



    val pagedAdapter by lazy {
        NotificationAdapter()
    }


    private lateinit var viewModel: NotificationsViewModel
    private lateinit var dialog: AppDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.homeFragment)

        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        dialog = AppDialog(requireContext())
        iv_back.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)

        }

        /**
        Paging adapter state listener
         **/
        if (checkNetwork(requireContext())) {
            pagedAdapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    dialog.show_dialog()
                } else {
                    dialog.dismiss_dialog()

                    // getting the error
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }

                    error?.let {
                        requireActivity().showShortToast(it.error.message)
                    }
                }
            }

            /**
             Setting up of Notification recycler view
             **/
            rv_notification.apply {
                adapter = pagedAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.VERTICAL,
                    false
                )
            }

            /**
             Corutines
             Collection data from viewModel and setting into paging adapter
             **/
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.notification.collect {
                    pagedAdapter.submitData(it)
                }

            }
        }else{
            requireActivity().showShortToast(getString(R.string.no_internet))
        }


    }

}