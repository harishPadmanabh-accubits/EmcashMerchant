package com.app.emcashmerchant.ui.loadEmcash

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.modelrequest.PayerAuthenticatorRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.fragment_em_pay_web_view.*
import timber.log.Timber


class EmPayWebViewFragment : Fragment(R.layout.fragment_em_pay_web_view) {
    private lateinit var viewModel: LoadEmcashViewModel
    lateinit var dialog: AppDialog
    var paymentCompleted:Boolean=false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(this){
//            findNavController().navigate(R.id.loadEmcashFragment)

            findNavController().popBackStack()

        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoadEmcashViewModel::class.java)
        dialog = AppDialog(requireActivity())

        var url3D = requireArguments().getString(KEY_URL3D)
        var orderId = requireArguments().getString(KEY_ORDERID)
        var sessionId = requireArguments().getString(KEY_SESSIONID)


        viewModel.amount = requireArguments().getString(KEY_AMOUNT)
        viewModel.description = requireArguments().getString(KEY_DESCRIPTION)
        viewModel.latitude = requireArguments().getDouble(KEY_LATITUDE)
        viewModel.longitude = requireArguments().getDouble(KEY_LONGITUDE)



        wv_empay.settings.javaScriptEnabled = true
        wv_empay.loadUrl(url3D.toString())

        wv_empay.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return  false
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (url.toString().contains("success")) {

                    var payerAuthenticator: PayerAuthenticatorRequest =
                        PayerAuthenticatorRequest(
                            String.format("%.2f", viewModel.amount?.toDouble()),
                            viewModel.description.toString(),
                            "07",
                            viewModel.latitude,
                            viewModel.longitude,
                            orderId.toString(),
                            sessionId.toString()
                        )

                    viewModel.payerAuthenticator(payerAuthenticator)

                }else if(url.toString().contains("Error")){
                    requireActivity().showShortToast("Payment Error")
                    findNavController().navigate(R.id.action_emPayWebViewFragment_to_walletFragment)

                }
            }


        }
        Timber.d("url3D : ${url3D}")

        observer()
    }

    private fun observer() {
        viewModel.apply {
            payerAuthenticatorStatus.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        paymentCompleted=true

                        requireActivity().showShortToast(getString(R.string.emcash_loaded))
                        findNavController().navigate(R.id.action_emPayWebViewFragment_to_walletFragment)
                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        requireActivity().showShortToast(it.errorMessage)
                        findNavController().navigate(R.id.action_emPayWebViewFragment_to_walletFragment)

                    }
                }

            })
        }

    }
}