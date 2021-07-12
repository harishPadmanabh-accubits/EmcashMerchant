package com.app.emcashmerchant.data.network.Repositories

import android.content.Context
import android.webkit.MimeTypeMap
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.models.*
import com.app.emcashmerchant.data.network.ApiManger
import com.app.emcashmerchant.utils.extensions.awaitResponse
import com.app.emcashmerchant.utils.extensions.getMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class SettingsRepository(val context: Context) {

    private val sessionStorage = SessionStorage(context)
    private val api = ApiManger(context).api

    fun performLogout(

        onApiCallback: (status: Boolean, message: String?, result: LogOutResponse?) -> Unit
    ) {
        api.performLogOut("Bearer ${sessionStorage.accesToken}").awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)
            }, onSuccess = {
                val data = it
                data?.let {
                    onApiCallback(true, null, data)
                }
            })
    }

    fun updateProfile(
        file: File,
        onApiCallback: (status: Boolean, message: String?, result: ProfileUpdateResponse?) -> Unit
    ) {

        val extension = MimeTypeMap.getFileExtensionFromUrl(file.toString())
        val profileImage = file.asRequestBody(getMediaType(extension).toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("profileImage", file.name, profileImage)

        api.performUpdateProfile(
            "Bearer ${sessionStorage.accesToken}",
            filePart
        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                onApiCallback(true, null, it)
            }
        )
    }

    fun profileDetails(
        onApiCallback: (status: Boolean, message: String?, result: ProfileDetailsResponse.Data?) -> Unit
    ) {


        api.getProfileDetails(
            "Bearer ${sessionStorage.accesToken}"

        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                val data=it?.data
                data.let {
                    onApiCallback(true, null, it)

                }
            }
        )
    }

    fun getTermsConditions(
        onApiCallback: (status: Boolean, message: String?, result: TermsConditionsResponse?) -> Unit
    ) {


        api.getTermsConditions(

        ).awaitResponse(
            onFailure = {
                onApiCallback(false, it, null)

            }, onSuccess = {
                it?.let {
                    onApiCallback(true, null, it)

                }
            }
        )
    }


}





