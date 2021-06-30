package com.app.emcashmerchant.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.app.emcashmerchant.R

class AppDialog(context: Context) {
    companion object {
        private lateinit var mcontext: Context
        lateinit var m_Dialog: Dialog
    }

    init {
        mcontext = context
        m_Dialog = Dialog(mcontext)
        m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        m_Dialog.setContentView(R.layout.progressbar)
        m_Dialog.setCancelable(false)
        m_Dialog.setCanceledOnTouchOutside(false)
        m_Dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show_dialog(): Dialog {
        m_Dialog.show()
        return m_Dialog
    }

    fun dismiss_dialog(): Dialog {
        m_Dialog.dismiss()
        return m_Dialog
    }
}
