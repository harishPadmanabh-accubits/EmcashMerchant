package com.app.emcashmerchant.utils

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//add all viewmodels here in when block in the format shown

class CustomViewModelFactory constructor( private val application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {

                when{
//                    isAssignableFrom(VIEWMODEL_NAME::class.java) ->
//                        VIEWMODEL(application)
                }



            }as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: CustomViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(CustomViewModelFactory::class.java) {
                    INSTANCE ?: CustomViewModelFactory(application)
                            .also { INSTANCE = it }
                }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}