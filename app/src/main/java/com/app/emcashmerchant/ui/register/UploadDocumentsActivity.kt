package com.app.emcashmerchant.ui.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.register.viewModel.RegisterViewModel
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.lay_documents_upload.*
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class UploadDocumentsActivity : AppCompatActivity() {

    private var selectedImageUriTrade: Uri? = null
    private var selectedImageUriComm: Uri? = null
    private var selectedImageUriBank: Uri? = null

    private val viewModel: RegisterViewModel by viewModels()

    private val sessionStorage by lazy {
        SessionStorage(this)
    }

    private val dialog by lazy {
        AppDialog(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documents)

        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e(task.exception, "Fetching FCM registration token failed")

                    return@OnCompleteListener
                }

                /**Get new FCM registration token**/
                viewModel.fcmToken = task.result.toString()
                Timber.d(viewModel.fcmToken.toString())
            })

        }catch (exception: Exception) {
            Timber.d(exception.toString())

        }

        setupObservers()




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

                doFinalSignUp()

            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }



    private fun selectTradeLicenceDoc() {
        iv_trade_file.apply {
            setImageResource(R.drawable.ic_upload_docs)
        }
        val intent = fileChooserIntent(IMAGE, PDF)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE_TRADEFILE)

    }

    private fun selectCommRegistrationDoc() {
        iv_commReg_file.apply {
            setImageResource(R.drawable.ic_upload_docs)
        }

        val intent = fileChooserIntent(IMAGE, PDF)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE_COMM)

    }

    private fun selectBankDetailsDoc() {
        iv_bankStatement_file.apply {
            setImageResource(R.drawable.ic_upload_docs)
        }
        val intent = fileChooserIntent(IMAGE, PDF)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE_BANK)

    }

    private fun fileChooserIntent(vararg types: String?): Intent? {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types)
        return intent
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
                        showLongToast(getString(R.string.doc_size_general_warning_message))

                    }

                }
                REQUEST_CODE_PICK_IMAGE_COMM -> {
                    selectedImageUriComm = data?.data

                    if (!FileSizeCheck(fetchFile(selectedImageUriComm))) {
                        viewModel.uploadCommercialRegistrationDoc(fetchFile(selectedImageUriComm))

                    } else {
                        showLongToast(getString(R.string.doc_size_general_warning_message))

                    }

                }
                REQUEST_CODE_PICK_IMAGE_BANK -> {
                    selectedImageUriBank = data?.data


                    if (!FileSizeCheck(fetchFile(selectedImageUriBank))) {
                        viewModel.uploadBankDetailsDoc(fetchFile(selectedImageUriBank))

                    } else {
                        showLongToast(getString(R.string.doc_size_general_warning_message))
                    }
                }

            }

        }
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
                        iv_commReg_file.setImageResource(R.drawable.ic_check)
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
                        iv_trade_file.setImageResource(R.drawable.ic_check)
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
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        iv_bankStatement_file.setImageResource(R.drawable.ic_check)

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        showShortToast(it.errorMessage)
                    }
                }
            })

        }
    }

    private fun doFinalSignUp() {
        if (selectedImageUriBank == null || selectedImageUriComm == null || selectedImageUriTrade == null) {
            showLongToast(getString(R.string.upload_all_documents))
        }else
        {
            viewModel.finalSignup(viewModel.fcmToken.toString())

        }

    }

    private fun fetchFile(uri: Uri?): File {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(uri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }

    companion object {
        private const val IMAGE = "image/*"
        private const val PDF = "application/pdf"
    }
}