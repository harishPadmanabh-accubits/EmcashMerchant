package com.app.emcashmerchant.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.app.emcashmerchant.data.model.RequestRetryModel
import com.app.emcashmerchant.ui.introScreen.IntroActivity
import com.app.emcashmerchant.utils.KEY_DEEPLINK
import com.app.emcashmerchant.utils.masterKeyAlias
import com.google.gson.Gson
import timber.log.Timber
import java.lang.Exception

@SuppressLint("CommitPrefEdits")
class SessionStorage(var _context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0
    private val prefName = "EmCashMerchant"
    private val keyReferenceId = "REFERENCE_ID"
    private val keyReferenceInitial = "REFERENCE_ID_INITIAL"
    private val keyReferenceOTP = "REFERENCE_ID_OTP"
    private val keyReferenceSecurityQuestion = "REFERENCE_ID_SECURITY_QUESTION"

    private val keyAccessToken = "ACCESS_TOKEN"
    private val keyRefreshToken = "REFRESH_TOKEN"
    private val keyMerchantEmail = "MERCHANT_EMAIL"
    private val keyMerchantNumber = "MERCHANT_NUMBER"
    private val keyMerchantName = "MERCHANT_NAME"
    private val keyBalance = "MERCHANT_BALANCE"
    private val keyProfileImage = "ProfileImage"


    var referenceIdInitial: String?
        get() = pref.getString(keyReferenceInitial, null)
        set(value) = editor.putString(keyReferenceInitial, value).apply()


    var referenceIdOtp: String?
        get() = pref.getString(keyReferenceOTP, null)
        set(value) = editor.putString(keyReferenceOTP, value).apply()


    var referenceIdSecurity: String?
        get() = pref.getString(keyReferenceSecurityQuestion, null)
        set(value) = editor.putString(keyReferenceSecurityQuestion, value).apply()

    var referenceId: String?
        get() = pref.getString(keyReferenceId, null)
        set(value) = editor.putString(keyReferenceId, value).apply()

    var accesToken: String?
        get() = pref.getString(keyAccessToken, null)
        set(value) = editor.putString(keyAccessToken, value).apply()

    var reuploadToken: String?
        get() = pref.getString("reupload_token", null)
        set(value) = editor.putString("reupload_token", value).apply()

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

    var balance: String?
        get() = pref.getString(keyBalance, "0")
        set(value) = editor.putString(keyBalance, value).apply()

    var isLoggedIn: Boolean
        get() = pref.getBoolean(keyMerchantNumber, false)
        set(value) = editor.putBoolean(keyMerchantNumber, value).apply()

    var profileImage: String?
        get() = pref.getString(keyProfileImage, null)
        set(value) = editor.putString(keyProfileImage, value).apply()


    fun logoutUser() {
        editor.clear()
        editor.commit()
        val i = Intent(_context, IntroActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        _context.startActivity(i)
    }

    fun clearUserReferenceId() {
        editor.clear()
        editor.commit()
    }

    var pendingDeeplink: String?
        get() = try {
            pref.getString(KEY_DEEPLINK, null)
        } catch (e: Exception) {
            Timber.e("Deeplink Exc $e")
            null
        }
        set(value) = editor.putString(KEY_DEEPLINK, value).apply()

    var routeRetryCount: Int
        get() = try {
            pref.getInt("retry_count", 0)
//
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
        set(value) {
            editor.putInt("retry_count", value).apply()
        }


    init {
        pref = EncryptedSharedPreferences.create(
            prefName,
            masterKeyAlias, _context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        editor = pref.edit()
    }
}