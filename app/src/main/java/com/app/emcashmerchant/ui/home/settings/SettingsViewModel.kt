package com.app.emcashmerchant.ui.home.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.data.network.ApiMapper
import com.app.emcashmerchant.data.network.Repositories.SettingsRepository
import timber.log.Timber
import java.io.File

class SettingsViewModel(val app: Application) : AndroidViewModel(app) {
    val initialLogOutResponseStatus = MutableLiveData<ApiMapper<LogOutResponse>>()
    val updateStatus = MutableLiveData<ApiMapper<ProfileUpdateResponse>>()
    val profileDetails = MutableLiveData<ApiMapper<ProfileDetailsResponse.Data>>()
    val termsConditionsResponse = MutableLiveData<ApiMapper<TermsConditionsResponse>>()

   private val repository =
        SettingsRepository(app)

    var isBankAccoutExists: Boolean = false


    fun performLogout(
    ) {
        initialLogOutResponseStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.performLogout() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    initialLogOutResponseStatus.value =
                        ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    initialLogOutResponseStatus.value =
                        ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun performUpdateProfile(
        file: File
    ) {
        updateStatus.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.updateProfile(file) { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    updateStatus.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    updateStatus.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun getTermsConditions(
    ) {

        termsConditionsResponse.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.getTermsConditions() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    termsConditionsResponse.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    termsConditionsResponse.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }

    fun getProfileDetails(
    ) {
        profileDetails.value = ApiMapper(ApiCallStatus.LOADING, null, null)

        repository.profileDetails() { status, message, result ->
            Timber.e("error $message")
            when (status) {
                true -> {
                    profileDetails.value = ApiMapper(ApiCallStatus.SUCCESS, result, null)
                }
                false -> {
                    profileDetails.value = ApiMapper(ApiCallStatus.ERROR, null, message)

                }
            }
        }
    }


}