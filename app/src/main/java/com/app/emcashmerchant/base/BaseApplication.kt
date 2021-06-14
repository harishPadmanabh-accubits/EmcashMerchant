package com.app.emcashmerchant.base

import android.app.Application
import com.app.emcashmerchant.BuildConfig
import timber.log.Timber

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}