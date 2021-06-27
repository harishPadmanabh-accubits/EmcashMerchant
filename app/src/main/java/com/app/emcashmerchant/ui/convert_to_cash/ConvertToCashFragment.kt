package com.app.emcashmerchant.ui.convert_to_cash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.emcashmerchant.R

class ConvertToCashFragment : Fragment() {

    companion object {
        fun newInstance() =
            ConvertToCashFragment()
    }

    private lateinit var viewModel: ConvertToCashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.convert_to_cash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConvertToCashViewModel::class.java)
        // TODO: Use the ViewModel
    }

}