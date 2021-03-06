package com.app.emcashmerchant.ui.transferPayment

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.CheckQrCodeRequest
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.isNull
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import kotlinx.android.synthetic.main.fragment_qr_code_scanner.*

class QrCodeScannerFragment : Fragment(R.layout.fragment_qr_code_scanner) {


    private lateinit var viewModel: TransferPaymentViewModel
    private lateinit var sessionStorage: SessionStorage

    private val PERMISSION_REQUEST = android.Manifest.permission.CAMERA

    /** variable to store Contact Permission Code*/
    private val CAMERA_PERMISSION_CODE = 2

    /** variable to store QR Scanner view*/
    private lateinit var codeScanner: CodeScanner

    /** variable to store the decoded text from QR code*/
    private var decodedText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.transferContactListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)


    }


    companion object {
        fun newInstance() =
            QrCodeScannerFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransferPaymentViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        checkPermission()

        observer(view)
        iv_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.transferContactListFragment)

        }


    }


    /**
    onResume method of fragment
     */
    override fun onResume() {
        super.onResume()
        if (this::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }

    }

    /**
    Method to check the permission
     */
    private fun checkPermission() {
        val permissionGranted = getPermissionStatus()
        if (permissionGranted) {
            setUpCodeScanner()
        } else {
            shouldShowRequestPermissionRationale(PERMISSION_REQUEST)
            requestPermissions(
                arrayOf(PERMISSION_REQUEST),
                CAMERA_PERMISSION_CODE
            )
        }
    }

    /** Method to get the status of permission
     *@return: Permission status flag
     */
    private fun getPermissionStatus(): Boolean {

        val isPermissionGranted = ActivityCompat.checkSelfPermission(
            context as Activity,
            PERMISSION_REQUEST
        ) == PackageManager.PERMISSION_GRANTED

        return isPermissionGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (getPermissionStatus()) {
            setUpCodeScanner()
        } else {
            // if user denies the permission request, handle the functionalities here.

        }
    }

    /**
    Setting the code scanner functions
     */
    private fun setUpCodeScanner() {
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, sv_qrcode_scanner)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                // the decoded text can be read from here.
                decodedText = it.text
                if (!decodedText.isNull()) {
                    val checkQrCodeRequest = CheckQrCodeRequest(decodedText.toString())
                    viewModel.checkQr(checkQrCodeRequest)

                } else {
                    requireActivity().showShortToast(getString(R.string.valid_qr))
                }
            }
        }
    }

    /**
    onPause method of fragment
     */
    override fun onPause() {
        super.onPause()
        if (this::codeScanner.isInitialized) {
            codeScanner.stopPreview()
        }
    }

    /**
    onDestroy method of fragment
     */
    override fun onDestroy() {
        super.onDestroy()
        if (this::codeScanner.isInitialized) {
            codeScanner.stopPreview()
        }
    }

    fun observer(view: View) {
        viewModel.apply {
            qrCodeCheckStatus.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {

                    }
                    ApiCallStatus.SUCCESS -> {
                        val bundle = bundleOf(
                            KEY_AMOUNT to it.data?.amount,
                            KEY_SENDER_NAME to it.data?.name,
                            KEY_SENDER_NUMBER to it.data?.phoneNumber,
                            KEY_REF_ID to decodedText,
                            KEY_LEVEL_COLOUR to it.data?.ppp.toString(),
                            KEY_ROLE to it.data?.roleId.toString()


                        )

                        Navigation.findNavController(view)
                            .navigate(R.id.performTransferPaymentFragment, bundle)

                    }
                    ApiCallStatus.ERROR -> {
                        requireActivity().showShortToast(it.errorMessage)

                    }
                }
            })
        }
    }


}