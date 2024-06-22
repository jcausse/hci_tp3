package com.grupo9.easyiot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.grupo9.easyiot.R
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.screens.DevicesState as DevicesState

@Composable
fun DashboardScreen(recentDevices: SnapshotStateList<String>, devicesState: DevicesState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Title(text = stringResource(R.string.recent_devices))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            if (devicesState !is DevicesState.Success){
                Text(text = stringResource(R.string.devices_error_msg))
            }
            else if (recentDevices.size == 0) {
                Text(text = stringResource(R.string.no_recent_devices_message))
            } else {
                val recentDevicesDetails: ArrayList<DeviceResult> = ArrayList()
                for (device in devicesState.get.result){
                    if (recentDevices.contains(device.id)){
                        recentDevicesDetails.add(device)
                    }
                }
                CardGridDev(recentDevicesDetails.reversed(),null,false)
            }
        }
    }
}
