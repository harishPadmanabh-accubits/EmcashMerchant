package com.app.emcashmerchant.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.app.emcashmerchant.ui.introScreen.IntroActivity
import com.app.emcashmerchant.utils.masterKeyAlias

@SuppressLint("CommitPrefEdits")
class SessionStorage(var _context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0
    private val prefName = "EmCashMerchant"
    private val keyReferenceId = "REFERENCE_ID"
    private val keyAccessToken = "ACCESS_TOKEN"
    private val keyRefreshToken = "REFRESH_TOKEN"
    private val keyMerchantEmail = "MERCHANT_EMAIL"
    private val keyMerchantNumber = "MERCHANT_NUMBER"
    private val keyMerchantName = "MERCHANT_Name"



    fun setReferenceIdSession(s: String?) {
        editor.putString(keyReferenceId, s)
        editor.commit()
    }

    fun getReferenceIdSession(): String? {
        return pref.getString(keyReferenceId, "")
    }

    var accesToken: String?
        get() = pref.getString(keyAccessToken, null)
        set(value) = editor.putString(keyAccessToken, value).apply()

    var refreshToken: String?
        get() = pref.getString(keyRefreshToken, null)
        set(value) = editor.putString(keyRefreshToken, value).apply()

    var merchantEmail: String?
        get() = pref.getString(keyMerchantEmail, null)
        set(value) = editor.putString(keyMerchantEmail, value).apply()

    var merchantNumber: String?
        get() = pref.getString(keyMerchantNumber, null)
        set(value) = editor.putString(keyMerchantNumber, value).apply()


    var merchantName: String?
        get() = pref.getString(keyMerchantName, null)
        set(value) = editor.putString(keyMerchantName, value).apply()

    var isLoggedIn: Boolean
        get() = pref.getBoolean(keyMerchantNumber, false)
        set(value) = editor.putBoolean(keyMerchantNumber, value).apply()


//    fun setLoginSession(s: String?) {
//        editor.putBoolean(IS_LOGIN, true)
//        editor.putString(KEY_LOGIN, s)
//        editor.commit()
//    }
//
//    val loginSession: HashMap<String, String?>
//        get() {
//            val user = HashMap<String, String?>()
//            user[KEY_LOGIN] = pref.getString(KEY_LOGIN, null)
//            return user
//        }


    fun logoutUser() {
        editor.clear()
        editor.commit()
        val i = Intent(_context, IntroActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        _context.startActivity(i)
    }

    companion object {

    }

    init {
//        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        pref = EncryptedSharedPreferences.create(
            prefName,
            masterKeyAlias, _context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        editor = pref.edit()
    }
}