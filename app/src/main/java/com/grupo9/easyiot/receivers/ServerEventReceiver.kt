package com.grupo9.easyiot.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.res.stringResource
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
import com.grupo9.easyiot.R
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.remote.model.EventData
import com.grupo9.easyiot.network.BASE_URL
import com.grupo9.easyiot.network.DeviceApi

class ServerEventReceiver : BroadcastReceiver() {

    private val gson = Gson()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Alarm received.")

        GlobalScope.launch(Dispatchers.IO) {
            val events = fetchEvents()
            events?.forEach {
                val deviceInfo: DeviceResult = DeviceApi.retorfitService.getDevice(it.deviceId).result
                val notificationTitle = if (deviceInfo.name != "") deviceInfo.name
                    else context?.getString(R.string.notification_channel_title)
                val notificationMessage = if (deviceInfo.name != "") assembleNotificationMessage(
                        context,
                        deviceInfo,
                        it.event,
                        it.args
                    )
                    else context?.getString(R.string.notification_default_message)
                Log.d(TAG, "Broadcasting send notification intent (${it.deviceId})")
                val intent2 = Intent().apply {
                    action = EasyIotIntent.SHOW_NOTIFICATION
                    `package` = EasyIotIntent.PACKAGE
                    putExtra(EasyIotIntent.DEVICE_ID, it.deviceId)
                    putExtra(EasyIotIntent.NOTIFICATION_TITLE, notificationTitle)
                    putExtra(EasyIotIntent.NOTIFICATION_MESSAGE, notificationMessage)

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

    private fun assembleNotificationMessage(context: Context?, info: DeviceResult, event: String, eventArgs: Map<String, String>) : String {
        val ret: String = when (event){
            /*** DOOR, BLINDS, SPEAKER, VACUUM ***/
            "statusChanged" -> msgHelper(eventArgs, "newStatus",
                context?.getString(R.string.notification_status_changed_prefix))

            /*** SPEAKER ***/
            "volumeChanged" -> msgHelper(eventArgs, "newVolume",
                context?.getString(R.string.notification_volume_changed_prefix))

            /*** SPEAKER ***/
            "genreChanged" -> msgHelper(eventArgs, "newGenre",
                context?.getString(R.string.notification_genre_changed_prefix))

            /*** DOOR ***/
            "lockChanged" -> msgHelper(eventArgs, "newLock",
                context?.getString(R.string.notification_lock_changed_prefix))

            /*** LAMP ***/
            "colorChanged" -> msgHelper(eventArgs, "newColor",
                context?.getString(R.string.notification_color_changed_prefix))

            /*** LAMP ***/
            "brightnessChanged" -> msgHelper(eventArgs, "newBrightness",
                context?.getString(R.string.notification_intensity_changed_prefix),
                suffix = "%")

            /*** FRIDGE, VACUUM ***/
            "modeChanged" -> msgHelper(eventArgs, "newMode",
                context?.getString(R.string.notification_mode_changed_prefix))

            /*** FRIDGE ***/
            "temperatureChanged" -> msgHelper(eventArgs, "newTemperature",
                context?.getString(R.string.notification_temperature_changed_prefix),
                suffix = "°C")

            /*** FRIDGE ***/
            "freezerTemperatureChanged" -> msgHelper(eventArgs, "newTemperature",
                context?.getString(R.string.notification_freezer_temperature_changed_prefix),
                suffix = "°C")

            /*** BLINDS ***/
            "levelChanged" -> msgHelper(eventArgs, "newLevel",
                context?.getString(R.string.notification_level_changed_prefix),
                suffix = "%")

            else -> ""
        }
        return ret
    }

    private fun msgHelper(map: Map<String, String>, key: String, prefix: String?, suffix: String = ""): String{
        val value = map.getOrDefault(key, "")
        return if (value == "") "" else "$prefix $value"
    }

    companion object {
        private const val TAG = "ServerEventReceiver"
    }
}