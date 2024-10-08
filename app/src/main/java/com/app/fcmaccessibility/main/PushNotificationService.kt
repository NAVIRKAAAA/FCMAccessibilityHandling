package com.app.fcmaccessibility.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.app.fcmaccessibility.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class PushNotificationService(

) : FirebaseMessagingService() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var notificationId = 0
    private lateinit var notificationManager: NotificationManager
    private var notificationAccessibilityManager: NotificationAccessibilityManager? = null


    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationAccessibilityManager = NotificationAccessibilityManager(this)
        createNotificationChannel()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        setFCMToken(token)
    }


    private fun setFCMToken(token: String) {
        return
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        scope.launch {
            if(notificationAccessibilityManager?.get()?.first() == false) return@launch

            val fcmNotification = message.data.toFCMNotification() ?: return@launch
            showNotification(fcmNotification.title, fcmNotification.message)
        }
    }

    private fun showNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.InboxStyle())


        notificationManager.notify(getNotificationId(), notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val soundUri = Uri.parse("android.resource://$packageName/") // + R.raw.fcmsound

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(
                    soundUri,
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                )
                this.vibrationPattern = vibrationPattern
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotificationId(): Int {
        return notificationId++
    }

    companion object {
        private const val CHANNEL_ID = "your_channel_id"
        private const val CHANNEL_NAME = "Channel name"
    }

}
