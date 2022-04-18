package com.app.emcashmerchant.firebaseService

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.TaskStackBuilder
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.ui.introScreen.SplashActivity
import com.app.emcashmerchant.utils.IS_FROM_DEEPLINK
import com.app.emcashmerchant.utils.KEY_DEEPLINK
import com.app.emcashmerchant.utils.KEY_TYPE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    var tokenOutput = ""
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.emcash.merchant.notifications"
    private val description = "notification"
    var title: String? = null
    var message: String? = null
    var deepLink: String? = null
    var Notificationtype: String? = null
    var rejectContent: String? = null

    lateinit var sessionStorage: SessionStorage

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data != null) {
            sessionStorage = SessionStorage(this)
            title = remoteMessage.data["title"]
            message = remoteMessage.data["message"]
            deepLink = remoteMessage.data["deepLink"]
            Notificationtype = remoteMessage.data["type"]
            rejectContent = remoteMessage.data["rejectContent"]

            Log.d("deepLinkNotfication", deepLink.toString())
            Log.d("typeNotification", Notificationtype.toString())

            showNotification(title, message)


        }


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        tokenOutput = token

    }


    private fun showNotification(title: String?, body: String?) {

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationIntent = Intent(applicationContext, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(KEY_DEEPLINK, deepLink)
            putExtra(KEY_TYPE, Notificationtype.toString())
            putExtra(IS_FROM_DEEPLINK, true)
        }

        val stackBuilder = TaskStackBuilder.create(this).also {
            it.addParentStack(SplashActivity::class.java)
            it.addNextIntent(notificationIntent)
        }
        val resultPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)


        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setSound(Uri.parse("android.resource://" + packageName + "/" + R.raw.notification))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setBadgeIconType(R.mipmap.ic_launcher_round)
        } else {

            builder = Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setSound(Uri.parse("android.resource://" + packageName + "/" + R.raw.notification))
                .setSmallIcon(R.mipmap.ic_launcher_round)

        }


        notificationManager.notify(10001, builder.build())

    }
}
