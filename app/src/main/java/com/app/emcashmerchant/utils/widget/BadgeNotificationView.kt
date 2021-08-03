package com.app.emcashmerchant.utils.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.justTry
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlinx.android.synthetic.main.layout_notification_with_badge_view.view.*
import timber.log.Timber
import java.lang.Exception

@SuppressLint("UnsafeExperimentalUsageError")
class BadgeNotificationView(context: Context, attrs: AttributeSet)
    : FrameLayout(context,attrs) {

    init {
        inflate(context, R.layout.layout_notification_with_badge_view, this)
        //setupBadge(5)
    }



}