package com.grupo9.easyiot.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.grupo9.easyiot.model.device.DeviceDetails
import com.grupo9.easyiot.network.DeviceDetailsApi
import kotlinx.coroutines.launch

class DeviceDetailsViewModel() : ViewModel() {
    var deviceDetailsState: DeviceDetailsState by mutableStateOf(DeviceDetailsState.Loading)
        private set

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
}

sealed interface DeviceDetailsState {
    data class Success(val get: DeviceDetails) : DeviceDetailsState
    data class Error(val message: String) : DeviceDetailsState
    data object Loading : DeviceDetailsState
}