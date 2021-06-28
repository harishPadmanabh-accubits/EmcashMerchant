package com.app.emcashmerchant.data

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.app.emcashmerchant.ui.introScreen.IntroActivity

class SessionStorage(var _context: Context)
{
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0
    private  val PREF_NAME = "EmCashMerchant"
    private  val IS_LOGIN = "IsLoggedIn"
    val KEY_LOGIN = "login"

    fun createLoginSession(s: String?)
    {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_LOGIN, s)
        editor.commit()
    }

    val loginSession: HashMap<String, String?>
        get() {
            val user = HashMap<String, String?>()
            user[KEY_LOGIN] = pref.getString(KEY_LOGIN, null)
            return user
        }



    val isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)

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
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}