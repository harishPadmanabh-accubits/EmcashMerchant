package com.app.emcashmerchant.ui.home.terms

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import kotlinx.android.synthetic.main.settings_fragment.iv_back
import kotlinx.android.synthetic.main.terms_fragment.*

class TermsFragment : Fragment() {

    companion object {
        fun newInstance() = TermsFragment()
    }

    private lateinit var viewModel: TermsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val callback = object : OnBackPressedCallback(true ) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.settingsFragment)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return inflater.inflate(R.layout.terms_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var  termscondition=requireArguments().getString("terms_conditions")
        tv_terms.text=termscondition
        iv_back.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TermsViewModel::class.java)


        // TODO: Use the ViewModel
    }



}