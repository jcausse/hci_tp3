package com.grupo9.easyiot

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.grupo9.easyiot.receivers.ServerEventReceiver
import com.grupo9.easyiot.network.NOTIFICATION_POLL_TIME_MS

class EasyIotApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        collectServerEvents()
    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_title)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply{
                description = descriptionText
            }

            with(NotificationManagerCompat.from(this)) {
                createNotificationChannel(channel)
            }
        }
    }
    private fun collectServerEvents() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ServerEventReceiver::class.java)

        var pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
        if (pendingIntent != null)
            alarmManager.cancel(pendingIntent)

        pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            SystemClock.currentThreadTimeMillis(),
            NOTIFICATION_POLL_TIME_MS,
            pendingIntent
        )
        Log.d(TAG, "Alarm set every $NOTIFICATION_POLL_TIME_MS millis")
    }
    companion object {
        const val TAG = "EasyIotApplication"
        const val CHANNEL_ID = "device"
    }
}
