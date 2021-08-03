package com.app.emcashmerchant.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.ui.login.LoginActivity
import com.app.emcashmerchant.ui.login.PinNumberActivity
import com.app.emcashmerchant.utils.KEY_BUISINESS_NAME
import com.app.emcashmerchant.utils.KEY_REVIEWSCREEN
import com.app.emcashmerchant.utils.extensions.isNull
import com.app.emcashmerchant.utils.extensions.openActivity
import kotlinx.android.synthetic.main.activity_account_under_process.*

class AccountUnderProcessActivity : AppCompatActivity() {
    lateinit var sessionStorage:SessionStorage
    private val description by lazy {
        intent.getStringExtra(KEY_REVIEWSCREEN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_under_process)
        sessionStorage= SessionStorage(this)
        if(description.equals("REUPLOAD")){
            textView2.text=getString(R.string.reupload_in_progress)

        }else{
            textView2.text=getString(R.string.reg_request_completed)

        }
        if(sessionStorage.isLoggedIn){
            btn_goto_login.text="Okay"

        }else{
            btn_goto_login.text=getString(R.string.take_me_back_to_login)

        }
    }
    fun onClick(view:View)
    {
        when(view.id) {
            R.id.btn_goto_login->{
                if(sessionStorage.isLoggedIn){
                    openActivity(PinNumberActivity::class.java)

                }else{
                    openActivity(LoginActivity::class.java)

                }
            }
        }
    }

    override fun onBackPressed() {
        if(sessionStorage.isLoggedIn){
            openActivity(PinNumberActivity::class.java)

        }else{
            openActivity(LoginActivity::class.java)

        }
    }
}
