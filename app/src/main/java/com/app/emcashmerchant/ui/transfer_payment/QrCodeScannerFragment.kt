package com.app.emcashmerchant.ui.transfer_payment

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.app.emcashmerchant.R
import com.app.emcashmerchant.ui.loadEmcash.LoadEmcashFragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import kotlinx.android.synthetic.main.fragment_qr_code_scanner.*

class QrCodeScannerFragment : Fragment() {

    private val PERMISSION_REQUEST = android.Manifest.permission.CAMERA

    /** variable to store Contact Permission Code*/
    private val CAMERA_PERMISSION_CODE = 2

    /** variable to store QR Scanner view*/
    private lateinit var codeScanner: CodeScanner

    /** variable to store the decoded text from QR code*/
    private var decodedText: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_code_scanner, container, false)
    }

    companion object {
        fun newInstance() =
            QrCodeScannerFragment()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()

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
                Log.e("decodedText",decodedText.toString());
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


}