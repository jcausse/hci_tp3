package com.grupo9.easyiot.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.grupo9.easyiot.EasyIotApplication
import com.grupo9.easyiot.EasyIotIntent
import com.grupo9.easyiot.MainActivity
import com.grupo9.easyiot.R

class ShowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == EasyIotIntent.SHOW_NOTIFICATION) {
            val deviceId: String? = intent.getStringExtra(EasyIotIntent.DEVICE_ID)
            Log.d(TAG, "Show notification intent received {$deviceId)")

            showNotification(context, deviceId!!)
        }
    }

    private fun showNotification(context: Context, deviceId: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EasyIotIntent.DEVICE_ID, deviceId)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val builder = NotificationCompat.Builder(context, EasyIotApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_logo)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText("Prueba123"/*context.getString(R.string.notification_text)*/) // TODO
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Prueba123"/*context.getString(R.string.notification_text2)*/) // TODO
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            if (notificationManager.areNotificationsEnabled())
                notificationManager.notify(deviceId.hashCode(), builder.build())
        } catch (e: SecurityException) {
            Log.d(TAG, "Notification permission not granted $e")
        }
    }

    companion object {
        private const val TAG = "ShowNotificationReceiver"
    }
}