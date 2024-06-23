package com.grupo9.easyiot.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.easyiot.model.device.DeviceDetails
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.State
import com.grupo9.easyiot.network.DeviceDetailsApi
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

class DeviceDetailsViewModel() : ViewModel() {
    var deviceDetailsState: DeviceDetailsState by mutableStateOf(DeviceDetailsState.Loading)
        private set
    var changeStatusResult: Boolean by mutableStateOf(true)
    var executeActionResult: Boolean by mutableStateOf(true)

    fun getDeviceDetails(id: String) {
        viewModelScope.launch {
            deviceDetailsState = DeviceDetailsState.Loading
            deviceDetailsState = try {
                val result = DeviceDetailsApi.retorfitService.getDevice(id)
                DeviceDetailsState.Success(result)
            } catch (e: Exception) {
                DeviceDetailsState.Error("Unexpected error: ${e.message}")
            }
        }
    }

    fun changeStatus(device: DeviceResult, status: Boolean) {
        viewModelScope.launch {
            changeStatusResult = try {
                val action = when (device.state) {
                    is State.LampState -> if (status) "turnOn" else "turnOff"
                    is State.SpeakerState -> if (status) "play" else "pause"
                    is State.FaucetState, is State.DoorState ->  if (status) "open" else "close"
                    else -> ""
                }
                if (action.isNotEmpty()) {
                    DeviceDetailsApi.retorfitService.changeStatus(device.id, action)
                } else { false }
            } catch (e: Exception) { false }
        }
    }

    fun listToJsonArray(data: List<Any>): List<JsonElement> {
        val jsonElements = mutableListOf<JsonElement>()

        var auxData = data
        if (auxData.isNotEmpty() && auxData[0] is List<*>) {

            @Suppress("UNCHECKED_CAST")
            auxData = auxData[0] as List<Any>
        }

        for (item in auxData) {
            when (item) {
                is String -> jsonElements.add(JsonPrimitive(item))
                is Number -> jsonElements.add(JsonPrimitive(item))
                is Boolean -> jsonElements.add(JsonPrimitive(item))
                else -> throw IllegalArgumentException("Unsupported type: ${item.javaClass}")
            }
        }
        return jsonElements
    }

    fun executeAction(id: String, action: String, value: Any) {
        viewModelScope.launch {
            executeActionResult = try {
                val result = DeviceDetailsApi.retorfitService.executeAction(id, action,listToJsonArray(listOf(value)))
                result.result
            } catch (e: Exception) {
                Log.e("DeviceViewModel", "API call failed: ${e.message}: Value: ${listToJsonArray(listOf(value))}")
                false }
        }
    }

    fun executeActionWithoutParameters(id: String, action: String) {
        viewModelScope.launch {
            executeActionResult = try {
                val result = DeviceDetailsApi.retorfitService.executeActionWithoutParameters(id, action)
                result.result
            } catch (e: Exception) { false }
        }
    }

}

sealed interface DeviceDetailsState {
    data class Success(val get: DeviceDetails) : DeviceDetailsState
    data class Error(val message: String) : DeviceDetailsState
    data object Loading : DeviceDetailsState
}

private fun isSwitchable(state: State): Boolean {
    return when (state) {
        is State.LampState, is State.SpeakerState, is State.VacuumState -> true
        else -> false
    }
}

private fun isOpenable(state: State): Boolean {
    return when (state) {
        is State.FaucetState, is State.DoorState -> true
        else -> false
    }
}