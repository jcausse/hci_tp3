package com.grupo9.easyiot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.grupo9.easyiot.model.device.DeviceResult

@Composable
fun DashboardScreen(recentDevices: SnapshotStateList<String>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Title(text = "Recent Devices")
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            if (recentDevices.size == 0) {
                Text(
                    text = "Devices will appear here as you interact with them.",
                )
            } else {
                //CardGridDev(recentDevices.toList())
                for (id in recentDevices) {
                 Text(text = id)
                }
            }
        }
    }
}
