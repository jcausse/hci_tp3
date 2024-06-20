package com.grupo9.easyiot.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.easyiot.model.device.Devices
import com.grupo9.easyiot.network.DeviceApi
import kotlinx.coroutines.launch
import java.io.IOException

class DevicesViewModel : ViewModel() {
    var devicesState: DevicesState by mutableStateOf(DevicesState.Loading)
        private set

    private val maxRecentDevices = 6
    val recentDevices: SnapshotStateList<String> = mutableStateListOf()

    init {
        getDevices()
    }
    fun getDevices(){
        viewModelScope.launch {
            devicesState = DevicesState.Loading
            devicesState = try {
                val result = DeviceApi.retorfitService.getDeviceList()
                DevicesState.Success(result)
            } catch (e: IOException) {
                println("Network error: ${e.message}")
                DevicesState.Error("Network error: ${e.message}")
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                DevicesState.Error("Unexpected error: ${e.message}")
            }
        }
    }
    fun addRecent(deviceId: String) {
        for (id in recentDevices){
            if (id == deviceId){
                return
            }
        }
        if (recentDevices.size == maxRecentDevices) {
            recentDevices.removeAt(0)
        }
        recentDevices.add(deviceId)
    }
}


sealed interface DevicesState{
    //success
    data class Success(val get: Devices) : DevicesState
    //error
    data class Error(val message: String) : DevicesState
    // loading
    data object Loading : DevicesState
}
