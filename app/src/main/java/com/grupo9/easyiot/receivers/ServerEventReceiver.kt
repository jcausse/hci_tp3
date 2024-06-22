package com.grupo9.easyiot.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.grupo9.easyiot.EasyIotIntent
import com.grupo9.easyiot.remote.model.EventData
import com.grupo9.easyiot.network.BASE_URL

class ServerEventReceiver : BroadcastReceiver() {

    private val gson = Gson()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Alarm received.")

        GlobalScope.launch(Dispatchers.IO) {
            val events = fetchEvents()
            events?.forEach {
                Log.d(TAG, "Broadcasting send notification intent (${it.deviceId})")
                val intent2 = Intent().apply {
                    action = EasyIotIntent.SHOW_NOTIFICATION
                    `package` = EasyIotIntent.PACKAGE
                    putExtra(EasyIotIntent.DEVICE_ID, it.deviceId)
                }
                context?.sendOrderedBroadcast(intent2, null)
            }
        }
    }

    private fun fetchEvents(): List<EventData>? {

        Log.d(TAG, "Fetching events...")
        val url = "${BASE_URL}devices/events"
        val connection = (URL(url).openConnection() as HttpURLConnection).also {
            it.requestMethod = "GET"
            it.setRequestProperty("Accept", "application/json")
            it.setRequestProperty("Content-Type", "text/event-stream")
            it.doInput = true
        }

        val responseCode = connection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val stream = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            val response = StringBuffer()
            val eventList = arrayListOf<EventData>()
            while (stream.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("data:") -> {
                        response.append(line!!.substring(5))
                    }

                    line!!.isEmpty() -> {
                        Log.d(TAG, response.toString())
                        val event = gson.fromJson<EventData>(
                            response.toString(),
                            object : TypeToken<EventData?>() {}.type
                        )
                        eventList.add(event)
                        response.setLength(0)
                    }
                }
            }
            stream.close()
            connection.disconnect()
            Log.d(TAG, "New events found (${eventList.size})")
            eventList
        } else {
            Log.d(TAG, "No new events found")
            null
        }
    }

    companion object {
        private const val TAG = "ServerEventReceiver"
    }
}