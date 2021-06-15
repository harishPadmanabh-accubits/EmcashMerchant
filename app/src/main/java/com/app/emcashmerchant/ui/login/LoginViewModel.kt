package com.app.emcashmerchant.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.utils.extensions.default

class LoginViewModel (val app:Application):AndroidViewModel(app){

    var passwordVisibiltyData = MutableLiveData<PasswordVisibilty>()
        .default(PasswordVisibilty.PSWD_HIDE)



}

enum class PasswordVisibilty{
    PSWD_SHOW,
    PSWD_HIDE
}