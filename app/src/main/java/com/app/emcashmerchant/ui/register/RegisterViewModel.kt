package com.app.emcashmerchant.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.SignupInitialRequestBody
import com.app.emcashmerchant.data.models.SignupInitialResponse
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.repositories.AuthRepository
import com.app.emcashmerchant.utils.extensions.default
import timber.log.Timber

class RegisterViewModel(val app: Application) : AndroidViewModel(app) {
    val repository = AuthRepository(app)

    var initialSignupStatus = MutableLiveData<ApiMapper<SignupInitialResponse.Data>>()

    fun performInitialSignup(
        address: String,
        businessName: String,
        contactPersonName: String,
        email: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        registeredNameOfBusiness: String,
        serviceDescription: String,
        tradeLicenseIssuingAuthority: String,
        tradeLicenseNumber: String,
        zipCode: String
    ) {
        initialSignupStatus.value = ApiMapper(ApiCallStatus.LOADING,null,null)
        val signupRequestBody = SignupInitialRequestBody(
            address,
            businessName,
            contactPersonName,
            email,
            firstName,
            lastName,
            phoneNumber,
            registeredNameOfBusiness,
            serviceDescription,
            tradeLicenseIssuingAuthority,
            tradeLicenseNumber,
            zipCode
        )
        repository.performInitialSignup(signupRequestBody) { status, message, result ->
            Timber.e("error $message")
            when(status){
                true->{
                    initialSignupStatus.value = ApiMapper(ApiCallStatus.SUCCESS,result,null)
                }
                false->{
                    initialSignupStatus.value = ApiMapper(ApiCallStatus.ERROR,null,message)

                }
            }
        }
    }
}