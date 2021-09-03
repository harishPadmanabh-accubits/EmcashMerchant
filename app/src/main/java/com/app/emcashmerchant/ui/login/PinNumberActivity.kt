package com.app.emcashmerchant.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.home.HomeBaseActivity
import com.app.emcashmerchant.utils.extensions.obtainViewModel
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showLongToast
import com.app.emcashmerchant.utils.extensions.showShortToast
import com.app.emcashmerchant.Authviewmodel.LoginViewModel
import com.app.emcashmerchant.DeepLinkFactory
import com.app.emcashmerchant.ui.ReUploadDocuments.ReUploadDocumentsActivity
import com.app.emcashmerchant.utils.*
import kotlinx.android.synthetic.main.activity_pin_number.*

class PinNumberActivity : AppCompatActivity() {

    private lateinit var sessionStorage: SessionStorage
    private lateinit var viewModel: LoginViewModel
    lateinit var dialog: AppDialog

    val isFromDeepLink by lazy {
        intent.getBooleanExtra(IS_FROM_DEEPLINK, false)
    }

    val deeplink by lazy {
        intent.getStringExtra(KEY_DEEPLINK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_number)
        initViews()
        getViewModel()
        observe()
        dialog = AppDialog(this)

    }

    fun onClick(view: View) {
        var pin: String = fet_pin.getInput()
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
                sessionStorage.logoutUser()
            }
        }
    }

    private fun initViews() {
        sessionStorage = SessionStorage(this)
    }

    private fun getViewModel() {
        viewModel = obtainViewModel(LoginViewModel::class.java)
    }

    private fun observe() {
        viewModel.pinNumberStatus.observe(this@PinNumberActivity, Observer {
            when (it.status) {
                ApiCallStatus.LOADING -> {
                    //show loading
                    dialog.show_dialog()

                }
                ApiCallStatus.SUCCESS -> {
                    dialog.dismiss_dialog()


                    var status = it.data?.data?.userStatus

                    if (isFromDeepLink) {
                        DeepLinkFactory.processDeeplink(deeplink, this)
                    } else {


                        if (it.data?.data?.requestingAddInfo == true) {
                            openActivity(ReUploadDocumentsActivity::class.java) {
                                putString(KEY_REUPLOAD_TOKEN, it.data?.data?.uploadDocumentToken)
                            }
                        } else if (status == 1) {//accept
                            openActivity(HomeBaseActivity::class.java)
                        } else if (status == 2) {
                            showShortToast("Your account is under review")
                        } else if (status == 3) {
                            showShortToast("Your account is rejected")
                        } else if (status == 4) {
                            showShortToast("Your account is blocked")
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
        ActivityCompat.finishAffinity(this)

    }

}