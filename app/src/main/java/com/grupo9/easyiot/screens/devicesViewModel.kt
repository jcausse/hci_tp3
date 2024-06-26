package com.grupo9.easyiot.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.grupo9.easyiot.network.DeviceApi
import kotlinx.coroutines.launch
import java.io.IOException

class DevicesViewModelFactory(private val isTablet: Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevicesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DevicesViewModel(isTablet) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DevicesViewModel(val isTablet: Boolean) : ViewModel() {
    var devicesState: DevicesState by mutableStateOf(DevicesState.Loading)
        private set

    private val maxRecentDevices = 6
    val recentDevices: SnapshotStateList<String> = mutableStateListOf()

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
