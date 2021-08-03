package com.app.emcashmerchant.ui.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.*
import com.app.emcashmerchant.Authviewmodel.RegisterViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.lay_documents_upload.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

class UploadDocumentsActivity : AppCompatActivity() {
    private var selectedImageUriTrade: Uri? = null
    private var selectedImageUriComm: Uri? = null
    private var selectedImageUriBank: Uri? = null
    private lateinit var viewModel: RegisterViewModel
    private var fcmToken:String?=null
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documents)
        initViews()
        initViewModel()
        setupObservers()
        dialog = AppDialog(this)


        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("fcmError", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                fcmToken = task.result.toString()
                Log.d("fcmToken007", fcmToken.toString())
            })

        }catch (exception: Exception){
            Log.d("exception", exception.toString())

        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ll_trade_licence_doc -> {
                selectTradeLicenceDoc()
            }
            R.id.ll_comm_reg_doc -> {
                selectCommRegistrationDoc()
            }
            R.id.ll_bank_details_doc -> {
                selectBankDetailsDoc()
            }
            R.id.btn_next -> {
                if (selectedImageUriBank == null || selectedImageUriComm == null || selectedImageUriTrade == null) {
                    showLongToast(getString(R.string.upload_all_documents))
                } else {
                    finalSignup()
                }
            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun selectTradeLicenceDoc() {
        iv_trade_file.apply {
            setImageResource(R.drawable.ic_upload_docs)
        }

        Intent(Intent.ACTION_PICK).also {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                REQUEST_CODE_PICK_IMAGE_TRADEFILE
            )
        }
//
//        Intent(Intent.ACTION_PICK).also {
//            it.type = "*/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/png", "application/pdf")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE_TRADEFILE)
//        }
    }

    private fun selectCommRegistrationDoc() {
        iv_commReg_file.apply {
            setImageResource(R.drawable.ic_upload_docs)
        }

        Intent(Intent.ACTION_PICK).also {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                REQUEST_CODE_PICK_IMAGE_COMM
            )
        }
//
//        Intent(Intent.ACTION_PICK).also {
//            it.type = "*/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/png", "application/pdf")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE_COMM)
//        }
    }

    private fun selectBankDetailsDoc() {
        iv_bankStatement_file.apply {
            setImageResource(R.drawable.ic_upload_docs)
        }

        Intent(Intent.ACTION_PICK).also {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                REQUEST_CODE_PICK_IMAGE_BANK
            )
        }

//        Intent(Intent.ACTION_PICK).also {
//            it.type = "*/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/png", "application/pdf")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE_BANK)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE_TRADEFILE -> {
                    selectedImageUriTrade = data?.data

                    if (!FileSizeCheck(fetchFile(selectedImageUriTrade))) {
                        viewModel.uploadTradeLicenseDoc(fetchFile(selectedImageUriTrade))

                    } else {
                        showLongToast(getString(R.string.trade_doc_size))

                    }

                }
                REQUEST_CODE_PICK_IMAGE_COMM -> {
                    selectedImageUriComm = data?.data



                    if (!FileSizeCheck(fetchFile(selectedImageUriComm))) {
                        viewModel.uploadCommercialRegistrationDoc(fetchFile(selectedImageUriComm))

                    } else {
                        showLongToast(getString(R.string.commercial_doc_size))

                    }

                }
                REQUEST_CODE_PICK_IMAGE_BANK -> {
                    selectedImageUriBank = data?.data


                    if (!FileSizeCheck(fetchFile(selectedImageUriBank))) {
                        viewModel.uploadBankDetailsDoc(fetchFile(selectedImageUriBank))

                    } else {
                        showLongToast(getString(R.string.bank_doc_size))
                    }
                }

            }

        }
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(RegisterViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.apply {

            finalSignupResponseStatus.observe(this@UploadDocumentsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        sessionStorage.clearUserReferenceId()
                        openActivity(AccountUnderProcessActivity::class.java)
                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })

            commercialDocumentStatus.observe(this@UploadDocumentsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()

                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val responseData = it.data
                        iv_commReg_file.apply {
                            setImageResource(R.drawable.ic_check)
                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })
            tradeDocumentStatus.observe(this@UploadDocumentsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val responseData = it.data

                        iv_trade_file.apply {
                            setImageResource(R.drawable.ic_check)
                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })
            bankDetailsDocumentStatus.observe(this@UploadDocumentsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val responseData = it.data

                        iv_bankStatement_file.apply {
                            setImageResource(R.drawable.ic_check)
                        }
                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })

        }
    }

    private fun finalSignup() {

    viewModel.finalSignup(fcmToken.toString())

    }

    private fun fetchFile(uri: Uri?): File {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(uri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }
}