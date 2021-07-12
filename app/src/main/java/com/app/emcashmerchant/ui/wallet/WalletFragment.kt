package com.app.emcashmerchant.ui.wallet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.convertEmcashTocash.ConvertEmcashToCashActivity
import com.app.emcashmerchant.ui.loadEmcash.LoadEmcashActivity
import com.app.emcashmerchant.utils.extensions.openActivity
import kotlinx.android.synthetic.main.wallet_fragment.*

class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        // TODO: Use the ViewModel

        btn_convert.setOnClickListener {
            requireActivity().openActivity(ConvertEmcashToCashActivity::class.java)
        }

        btn_load_emcash.setOnClickListener{
            requireActivity().openActivity(LoadEmcashActivity::class.java);
        }
    }

}