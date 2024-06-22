package com.grupo9.easyiot.screens

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

class DeviceDetailsViewModel() : ViewModel() {
    var deviceDetailsState: DeviceDetailsState by mutableStateOf(DeviceDetailsState.Loading)
        private set
    var changeBrightnessResult: Boolean by mutableStateOf(true)
    var changeStatusResult: Boolean by mutableStateOf(true)
    var changeLevelResult: Boolean by mutableStateOf(true)
    var changeVolumeResult: Boolean by mutableStateOf(true)

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

    fun changeBrightness(device: DeviceResult, brightness: Int) {
        viewModelScope.launch {
            changeBrightnessResult = try {
                // Switch case used to access LampState
                when (device.state) {
                    is State.LampState -> {
                        DeviceDetailsApi.retorfitService.changeBrightness(device.id, "setBrightness")
                    }
                    else -> { false }
                }
            } catch (e: Exception) { false }
        }
    }

    fun changeLampStatus(device: DeviceResult, status: Boolean) {
        viewModelScope.launch {
            changeStatusResult = try {
                // Switch case used to access LampState
                when (device.state) {
                    is State.LampState -> {
                        // Convert from Boolean to String
                        val action = if (status) "turnOff" else "turnOn"
                        DeviceDetailsApi.retorfitService.changeLampStatus(device.id, action)
                    }
                    else -> { false }
                }
            } catch (e: Exception) { false }
        }
    }

    fun changeLevel(device: DeviceResult, level: Int) {
        viewModelScope.launch {
            changeLevelResult = try {
                // Switch case used to access LampState
                when (device.state) {
                    is State.BlindsState -> {
                        DeviceDetailsApi.retorfitService.changeLevel(device.id, "setLevel")
                    }
                    else -> { false }
                }
            } catch (e: Exception) { false }
        }
    }

    fun changeVolume(device: DeviceResult, volume: Int) {
        viewModelScope.launch {
            changeVolumeResult = try {
                // Switch case used to access LampState
                when (device.state) {
                    is State.SpeakerState -> {
                        // Create an updated state for the lamp
                        val updatedState: State.SpeakerState = State.SpeakerState(
                            (device.state as State.SpeakerState).status,
                            volume,
                            (device.state as State.SpeakerState).genre,
                            (device.state as State.SpeakerState).song,
                        )
                        // Create an updated device
                        val updatedDevice = DeviceResult(
                            device.id,
                            device.name,
                            device.type,
                            updatedState,
                            device.meta
                        )
                        val deviceDetails = DeviceDetails(result = updatedDevice)
                        DeviceDetailsApi.retorfitService.changeVolume(device.id, deviceDetails)
                    }
                    else -> { false }
                }
            } catch (e: Exception) { false }
        }
    }
}

sealed interface DeviceDetailsState {
    data class Success(val get: DeviceDetails) : DeviceDetailsState
    data class Error(val message: String) : DeviceDetailsState
    data object Loading : DeviceDetailsState
}