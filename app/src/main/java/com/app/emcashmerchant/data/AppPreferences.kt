package com.app.emcashmerchant.data

import android.content.Context
import android.content.SharedPreferences
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.putAny


object AppPreferences {
    private var preferences: SharedPreferences? = null

    fun init(context: Context): AppPreferences {
        preferences = context.getSharedPreferences("${context.getString(
            R.string.app_name
        )}_prefs", Context.MODE_PRIVATE)
        return this
    }

    fun setLastLaunchTime(time:Long){
        preferences?.putAny("last_launch_time",time)
    }

    fun getLastLaunchTime():Long? =
        preferences?.getLong("last_launch_time",0L)

}