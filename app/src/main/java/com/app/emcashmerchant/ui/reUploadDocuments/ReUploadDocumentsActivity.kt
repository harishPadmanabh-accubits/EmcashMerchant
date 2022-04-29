package com.app.emcashmerchant.ui.reUploadDocuments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.reUploadDocuments.viewModel.ReUploadDocumentsViewModel
import com.app.emcashmerchant.ui.register.AccountUnderProcessActivity
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.lay_documents_upload.*
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ReUploadDocumentsActivity : AppCompatActivity() {
    private lateinit var viewModel: ReUploadDocumentsViewModel
    private lateinit var dialog: AppDialog
    private var selectedImageUriTrade: Uri? = null
    private var selectedImageUriComm: Uri? = null
    private var selectedImageUriBank: Uri? = null
    private val token by lazy {
        intent.getStringExtra(KEY_REUPLOAD_TOKEN)
    }

    lateinit var fileTrade: File
    lateinit var fileComm: File
    lateinit var fileBank: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_upload_documents)
        if(!token.isNullOrEmpty()){
            SessionStorage(this).reuploadToken = token
        }else{
            Timber.e("hhp upload token empty")
        }
        viewModel = ViewModelProvider(this).get(ReUploadDocumentsViewModel::class.java)
      //  viewModel.reUploadUserDetails(token.toString())
        dialog = AppDialog(this)

        observer()


    }

    fun submit() {


        if (selectedImageUriBank == null || selectedImageUriComm == null || selectedImageUriTrade == null) {
            showLongToast(getString(R.string.upload_all_documents))

        } else {


            viewModel.submitForReview(
                fetchFile(selectedImageUriTrade),
                fetchFile(selectedImageUriComm),
                fetchFile(selectedImageUriBank),
                token.toString(),
                "true"
            )
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
            R.id.btn_reUpload -> {

                submit()

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

        Intent(Intent.ACTION_PICK).also {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                REQUEST_CODE_PICK_IMAGE_TRADEFILE
            )
        }

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

    }

    private fun observer() {
        viewModel.apply {
            reUploadUserDetailsStatus.observe(this@ReUploadDocumentsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                    }
                    ApiCallStatus.ERROR -> {
                        showShortToast(it.errorMessage)
                        dialog.dismiss_dialog()

                    }
                }

            })
            reUploadCommercialDocument.observe(this@ReUploadDocumentsActivity, Observer {
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
            reUploadTradeLicenseDocument.observe(this@ReUploadDocumentsActivity, Observer {
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
            reUploadBankDocument.observe(this@ReUploadDocumentsActivity, Observer {
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

            submitForReview.observe(this@ReUploadDocumentsActivity, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        val responseData = it.data

                        openActivity(AccountUnderProcessActivity::class.java){
                            putString(KEY_REVIEWSCREEN,REUPLOAD)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE_TRADEFILE -> {
                    selectedImageUriTrade = data?.data

                    if (!FileSizeCheck(fetchFile(selectedImageUriTrade))) {
                        viewModel.reUploadTradeLicenseDoc(
                            token.toString(),
                            fetchFile(selectedImageUriTrade),
                            "false"
                        )

                    } else {
                        showLongToast(getString(R.string.trade_doc_size))

                    }

                }
                REQUEST_CODE_PICK_IMAGE_COMM -> {
                    selectedImageUriComm = data?.data

                    if (!FileSizeCheck(fetchFile(selectedImageUriComm))) {
                        viewModel.reUploadCommercialDoc(
                            token.toString(),
                            fetchFile(selectedImageUriTrade),
                            "false"
                        )


                    } else {
                        showLongToast(getString(R.string.commercial_doc_size))

                    }

                }
                REQUEST_CODE_PICK_IMAGE_BANK -> {
                    selectedImageUriBank = data?.data
                    if (!FileSizeCheck(fetchFile(selectedImageUriBank))) {
                        viewModel.reUploadBankDetailsDoc(
                            token.toString(),
                            fetchFile(selectedImageUriTrade),
                            "false"
                        )


                    } else {
                        showLongToast(getString(R.string.bank_doc_size))
                    }
                }

            }

        }
    }

    private fun fetchFile(uri: Uri?): File {

        val parcelFileDescriptor = uri?.let { contentResolver.openFileDescriptor(it, "r", null) }
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(uri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }

}