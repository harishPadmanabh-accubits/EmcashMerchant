package com.app.emcashmerchant.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.KEY_REF_ID
import com.app.emcashmerchant.utils.extensions.putAny


object AppPreferences {

    private lateinit var preferences: SharedPreferences

    fun init(context: Context): AppPreferences {
        preferences = EncryptedSharedPreferences.create(
            "${context.getString(R.string.app_name)}_prefernces",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return this
    }

    var refId: String?
        get() = preferences.getString(KEY_REF_ID, "")
        set(value) = preferences.edit().putString(KEY_REF_ID, value).apply()


}