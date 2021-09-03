package com.app.emcashmerchant.service

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
    var type: String? = null
    var rejectContent: String? = null

    lateinit var sessionStorage: SessionStorage

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data != null) {
            sessionStorage = SessionStorage(this)
            title = remoteMessage.data["title"]
            message = remoteMessage.data["message"]
            deepLink = remoteMessage.data["deepLink"]
            type = remoteMessage.data["type"]
            rejectContent = remoteMessage.data["rejectContent"]

            Log.d("deepLink",deepLink.toString())
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
            putExtra(KEY_DEEPLINK, deepLink?.toString())
            putExtra(KEY_TYPE, type?.toString())
            putExtra(IS_FROM_DEEPLINK,true)
        }

        val stackBuilder = TaskStackBuilder.create(this).also {
            it.addParentStack(SplashActivity::class.java)
            it.addNextIntent(notificationIntent)
        }
        val resultPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        //  notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        //    notificationIntent.putExtra(KEY_DEEPLINK, deepLink.toString())
        //  notificationIntent.putExtra(KEY_TYPE, type.toString())
        //deepLink?.let { Log.d("KEY_DEEPLINK_SHOW_NOT", it) }

//        var contentIntent = PendingIntent.getActivity(
//            this,
//            12345,
//            notificationIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )

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
                .setSmallIcon(com.app.emcashmerchant.R.mipmap.ic_launcher)
                .setBadgeIconType(R.mipmap.ic_launcher)
        } else {

            builder = Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setSound(Uri.parse("android.resource://" + packageName + "/" + R.raw.notification))
                .setSmallIcon(com.app.emcashmerchant.R.mipmap.ic_launcher)

        }


        notificationManager.notify(10001, builder.build())

    }
}
