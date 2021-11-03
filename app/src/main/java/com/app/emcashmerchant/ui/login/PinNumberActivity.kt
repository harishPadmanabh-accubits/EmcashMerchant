package com.app.emcashmerchant.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.ui.login.viewModel.LoginViewModel
import com.app.emcashmerchant.DeepLinkFactory
import com.app.emcashmerchant.ui.reUploadDocuments.ReUploadDocumentsActivity
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.AccountType.ACCOUNT_BLOCKED
import com.app.emcashmerchant.utils.AccountType.ACCOUNT_IN_REVIEW
import com.app.emcashmerchant.utils.AccountType.ACCOUNT_REJECTED
import com.app.emcashmerchant.utils.AccountType.ACCOUNT_VERIFIED
import kotlinx.android.synthetic.main.activity_pin_number.*
import kotlinx.android.synthetic.main.fragment_payment_chat_history.*
import timber.log.Timber

class PinNumberActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private val sessionStorage by lazy {
        SessionStorage(this)
    }
    private val dialog by lazy {
        AppDialog(this)

    }
    private val isFromDeepLink by lazy {
        intent.getBooleanExtra(IS_FROM_DEEPLINK, false)
    }

    private val deepLink by lazy {
        intent.getStringExtra(KEY_DEEPLINK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_number)
        observe()

    }

    override fun onResume() {
        super.onResume()
        fet_pin.requestFocus()

    }

    fun onClick(view: View) {
        val pin: String = fet_pin.getInput()
        when (view.id) {
            R.id.btn_confirm_pin -> {
                if (pin.length < 6) {
                    showLongToast(getString(R.string.enter_valid_pin))
                } else {
                    goToHomePage(pin.toInt())

                }
            }
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.ll_logout -> {
                viewModel.performLogout()
            }
        }
    }


    private fun observe() {

        viewModel.initialLogOutResponseStatus.observe(this, Observer {
            when (it.status) {
                ApiCallStatus.LOADING -> {
                    dialog.show_dialog()
                }
                ApiCallStatus.SUCCESS -> {
                    dialog.dismiss_dialog()
                    sessionStorage.logoutUser()
                }
                ApiCallStatus.ERROR -> {
                    dialog.dismiss_dialog()
                    sessionStorage.logoutUser()
                    showShortToast(it.errorMessage)
                }
            }
        })

        viewModel.pinNumberStatus.observe(this@PinNumberActivity, Observer {
            when (it.status) {
                ApiCallStatus.LOADING -> {
                    dialog.show_dialog()

                }
                ApiCallStatus.SUCCESS -> {
                    dialog.dismiss_dialog()

                    val status = it.data?.data?.userStatus
                    Timber.d("deepLinkPinNumberScreen $isFromDeepLink")

                    if (isFromDeepLink) {
                        DeepLinkFactory.processDeeplink(deepLink, this)
                    } else {
                        if (it.data?.data?.requestingAddInfo == true) {//requested for documents
                            openActivity(ReUploadDocumentsActivity::class.java) {
                                putString(KEY_REUPLOAD_TOKEN, it.data.data.uploadDocumentToken)
                            }
                        } else if (status == ACCOUNT_VERIFIED) {
                            openActivity(HomeBaseActivity::class.java)
                        } else if (status == ACCOUNT_IN_REVIEW) {
                            showShortToast(getString(R.string.account_in_review))
                        } else if (status == ACCOUNT_REJECTED) {
                            showShortToast(getString(R.string.account_rejected))
                        } else if (status == ACCOUNT_BLOCKED) {
                            showShortToast(getString(R.string.account_blocked))
                        }
                    }
                }
                ApiCallStatus.ERROR -> {
                    dialog.dismiss_dialog()
                    showShortToast(it.errorMessage)
                }
            }
        })

    }

    private fun goToHomePage(pin: Int) {
        viewModel.performPinNumberVerify(pin)
    }

    override fun onBackPressed() {
        finishAffinity()

    }

}