package com.app.emcashmerchant.ui.transaction_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.FilterDurationResponse
import com.app.emcashmerchant.ui.transaction_history.adapters.DurationAdapter
import com.app.emcashmerchant.ui.transaction_history.adapters.DurationItemClickListener
import com.app.emcashmerchant.ui.transaction_history.adapters.TransactionsTabAdapter
import com.app.emcashmerchant.utils.extensions.dateFormatFromCalender
import com.app.emcashmerchant.utils.extensions.getDaysAgo
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.google.android.material.tabs.TabLayoutMediator
import com.savvi.rangedatepicker.CalendarPickerView
import kotlinx.android.synthetic.main.lay_duration_filter.*
import kotlinx.android.synthetic.main.lay_types_filter.*
import kotlinx.android.synthetic.main.layout_payment_receipt_bottom.*
import kotlinx.android.synthetic.main.transaction_history_fragment.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TransactionHistoryFragment : Fragment(), DurationItemClickListener {

    companion object {
        fun newInstance() =
            TransactionHistoryFragment()
    }

    private lateinit var viewModel: TransactionHistoryViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private var startDate: String = ""
    private var endDate: String = ""
    private var durationFilterCustom = 0

    var listDates = java.util.ArrayList<Date>()
    var dateArray = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
        return inflater.inflate(R.layout.transaction_history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        durationData()
        initViewModel(requireActivity())
        setupTabs()
        setupViews(view)

        ll_holder.visibility = View.GONE
        calenderView.visibility = View.GONE
//        ll_calenderController.visibility=View.GONE

        val timeZone = TimeZone.getDefault();
        val locale = Locale.getDefault();

        val nextYear = Calendar.getInstance(timeZone, locale)
        nextYear.add(Calendar.YEAR, 1)


        val lastYear = Calendar.getInstance()
        lastYear.add(Calendar.YEAR, -2)

        calenderView.init(lastYear.time, nextYear.time) //
            .inMode(CalendarPickerView.SelectionMode.RANGE)
            .withSelectedDate(Date())







        iv_back_type.setOnClickListener {
            fl_holder.visibility = View.GONE

        }
        iv_duration_back.setOnClickListener {
            typeViewVisible()
        }


        btn_filter.setOnClickListener {

            if (durationFilterCustom == 0) {
                if (startDate.isEmpty()) {
                    requireActivity().showShortToast("Select a Date")
                } else {

                    dateArray.add(0, startDate)
                    dateArray.add(1,  getDaysAgo(0).toString())

                    sharedViewModel.sendDate(dateArray)
                    fl_holder.visibility = View.GONE


                }
            } else if (durationFilterCustom == 1) {
                listDates = calenderView.selectedDates as java.util.ArrayList<Date>

                if (listDates.size <= 1) {
                    requireActivity().showShortToast("Select a Dates")
                } else {

                    startDate = listDates[0].toString()
                    endDate = listDates[listDates.size - 1].toString()
                    dateArray.add(0, startDate)
                    dateArray.add(1, endDate)

                    sharedViewModel.sendDate(dateArray)
                    fl_holder.visibility = View.GONE
                    tv_toDate.text = dateFormatFromCalender(endDate)
                    tv_fromDate.text = dateFormatFromCalender(startDate)


                }

            }


        }

        rg_type.setOnCheckedChangeListener { group, checkedId ->

            rb_emcashSent.setOnClickListener {
                viewpager_tabs.currentItem = 2
                fl_holder.visibility = View.GONE

            }
            rb_emcashrecieved.setOnClickListener {
                viewpager_tabs.currentItem = 1
                fl_holder.visibility = View.GONE

            }
            rb_rejected.setOnClickListener {
                sharedViewModel.sendStatus("4")
                fl_holder.visibility = View.GONE

            }
            rb_failed.setOnClickListener {
                sharedViewModel.sendStatus("3")
                fl_holder.visibility = View.GONE

            }
            rb_pending.setOnClickListener {
                sharedViewModel.sendStatus("2")
                fl_holder.visibility = View.GONE

            }
            rb_success.setOnClickListener {
                sharedViewModel.sendStatus("1")
                fl_holder.visibility = View.GONE


            }
            rb_date.setOnClickListener {
                durationViewVisible()
            }

        }

        sharedViewModel.type.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it==true){
                rg_type.clearCheck()
                tv_fromDate.text=""
                tv_toDate.text=""
                calenderView.clearSelectedDates()

            }

        })
//        pagedTransactions.observe(viewLifecycleOwner, Observer {
//            pagedAdapter.submitData(lifecycle,it)
//            Timber.e("Observing ${it}")
//        })

    }

    private fun initViewModel(fragmentActivity: FragmentActivity?) {
        if (fragmentActivity != null)
            viewModel = fragmentActivity.obtainViewModel(TransactionHistoryViewModel::class.java)
        else
            Timber.e("ViewModel init failed")

    }

    private fun setupViews(view: View) {
        iv_back.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
        iv_filter.setOnClickListener {
            //setup bottom sheet
            if (fl_holder.isVisible) {
                fl_holder.visibility = View.GONE
            } else {
                fl_holder.visibility = View.VISIBLE
                typeViewVisible()


            }
        }
        calenderView.setOnDateSelectedListener(object :CalendarPickerView.OnDateSelectedListener{
            override fun onDateSelected(date: Date?) {
                updateUIwithCalendar(calenderView.selectedDates)
            }

            override fun onDateUnselected(date: Date?) {
            }

        })
    }

    private fun setupTabs() {
        viewpager_tabs.adapter = TransactionsTabAdapter(requireActivity())
        TabLayoutMediator(tab_layout, viewpager_tabs) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.all)
                1 -> tab.text = getString(R.string.inbound)
                2 -> tab.text = getString(R.string.outbound)
            }
        }.attach()
    }


    private fun typeViewVisible() {
        ll_type.visibility = View.VISIBLE
        ll_duration.visibility = View.GONE
    }

    private fun durationViewVisible() {
        ll_type.visibility = View.GONE
        ll_duration.visibility = View.VISIBLE
    }

    private fun durationData() {

        val durations = ArrayList<FilterDurationResponse>()
        durations.add(FilterDurationResponse(1, "2 Days"))
        durations.add(FilterDurationResponse(2, "1 Week"))
        durations.add(FilterDurationResponse(3, "1 Month"))
        durations.add(FilterDurationResponse(4, "Custom"))

        rv_duration.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = DurationAdapter(durations, this@TransactionHistoryFragment)
        }
    }

    override fun onDurationClicked(duration: FilterDurationResponse) {

        if (duration.id == 4) {
            ll_holder.visibility = View.VISIBLE
            calenderView.visibility = View.VISIBLE
            startDate = ""
            endDate=""

            durationFilterCustom = 1

        } else if (duration.id == 3) {
            ll_holder.visibility = View.GONE
            calenderView.visibility = View.GONE
            startDate = getDaysAgo(30).toString()
            endDate=""

            durationFilterCustom = 0

        } else if (duration.id == 2) {
            ll_holder.visibility = View.GONE
            calenderView.visibility = View.GONE
            startDate = getDaysAgo(7).toString()
            durationFilterCustom = 0
        } else if (duration.id == 1) {
            ll_holder.visibility = View.GONE
            calenderView.visibility = View.GONE
            endDate=""
            startDate = getDaysAgo(2).toString()
            durationFilterCustom = 0
        }
    }
    fun dateFormatFromCalender(dateFormat: String, dateStr: String): String {
        val utc = TimeZone.getTimeZone("UTC")
        val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
        val destFormat = SimpleDateFormat(dateFormat)
        sourceFormat.timeZone = utc
        val convertedDate = sourceFormat.parse(dateStr)
        return destFormat.format(convertedDate)
    }
    private fun updateUIwithCalendar(selectedDates: List<Date>) {

        tv_toDate.text = dateFormatFromCalender("dd-MMM-YYYY", selectedDates.last().toString())
        tv_fromDate.text = dateFormatFromCalender("dd-MMM-YYYY", selectedDates.first().toString())
    }
}