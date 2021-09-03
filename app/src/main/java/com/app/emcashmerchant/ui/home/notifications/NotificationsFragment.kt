package com.app.emcashmerchant.ui.home.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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

class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() =
            NotificationsFragment()
    }

    val pagedAdapter by lazy {
        NotificationAdapter()
    }


    private lateinit var viewModel: NotificationsViewModel
    private lateinit var dialog: AppDialog


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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        dialog = AppDialog(requireActivity())
        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }

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

            rv_notification.apply {
                adapter = pagedAdapter
                layoutManager = LinearLayoutManager(
                    requireActivity(),
                    RecyclerView.VERTICAL,
                    false
                )
            }

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