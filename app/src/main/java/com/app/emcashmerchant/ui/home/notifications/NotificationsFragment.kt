package com.app.emcashmerchant.ui.home.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.home.notifications.adapter.NotificationAdapter
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.notifications_fragment.*

class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() =
            NotificationsFragment()
    }

    private lateinit var viewModel: NotificationsViewModel
    private lateinit var dialog: AppDialog

    private var page = 1
    private var limit: Int = 300
    lateinit var mlayoutManager: LinearLayoutManager
    lateinit var adapter: NotificationAdapter

    var mIsLoading = false // fetching more data

    var isMoreDataAvailable = true // more da
    var mTotalNoOfData:Int=0 // more da


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
        dialog = AppDialog(requireActivity())
        mlayoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL,
            false
        )
        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeFragment)

        }
        viewModel.notifications(page, limit)

        observer()


    }


    fun observer() {
        viewModel.apply {
            notificationStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        mTotalNoOfData = it.data?.count!!
                        it.data?.rows.let {
                            val groupedActivies = groupNotification(it)
                            requireActivity().showShortToast(groupedActivies.toString())

                            rv_notification.apply {
                                layoutManager = mlayoutManager
                                adapter = NotificationAdapter(groupedActivies)

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