package com.grupo9.easyiot.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.grupo9.easyiot.EasyIotIntent

class SkipNotificationReceiver(private val deviceId: String) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(EasyIotIntent.SHOW_NOTIFICATION) &&
            intent.getStringExtra(EasyIotIntent.DEVICE_ID).equals(deviceId)
        ) {
            Log.d(TAG, "Skipping notification send ($deviceId)")
            abortBroadcast()
        }
    }

    companion object {
        private const val TAG = "SkipNotificationReceiver"
    }
}