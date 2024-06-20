package com.grupo9.easyiot.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.Devices

class DashboardViewModel : ViewModel(){
    val recentDevices: SnapshotStateList<DeviceResult> = mutableStateListOf()

    fun addDevice(device: DeviceResult) {
        recentDevices.add(device)
    }
    fun getRecentDevices(): List<DeviceResult> {
        return recentDevices.toList()
    }
}
