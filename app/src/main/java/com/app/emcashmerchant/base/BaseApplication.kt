package com.app.emcashmerchant.base

import android.app.Application
import com.app.emcashmerchant.BuildConfig
import com.google.firebase.FirebaseApp
import timber.log.Timber

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}