package com.grupo9.easyiot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.grupo9.easyiot.DeviceCard
import com.grupo9.easyiot.R
import com.grupo9.easyiot.model.device.DeviceResult

val kodchasan = FontFamily(
    Font(R.font.kodchasan_regular, FontWeight.Normal),
    Font(R.font.kodchasan_bold, FontWeight.Bold),
)

@Composable
fun DevicesScreen(
    devicesState: DevicesState,
    onDeviceClick: ((String) -> Unit),
    isTablet: Boolean,
    onDeviceDetailsClick: (id: String) -> Unit,
    refreshDevices: () -> Unit
) {
    LaunchedEffect(Unit) {
        refreshDevices()
    }
    when (devicesState) {
        is DevicesState.Loading -> {
            LoadingScreen()
        }
        is DevicesState.Success -> {
            SuccessScreen(devicesState.get.result, onDeviceClick, isTablet, onDeviceDetailsClick)
        }
        is DevicesState.Error -> {
            ErrorScreen()
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        Text(text =  stringResource(R.string.loading_msg))
    }
}
@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        Text(text =  stringResource(R.string.devices_error_msg))
    }
}

@Composable
fun SuccessScreen(
    devices : ArrayList<DeviceResult>,
    onDeviceClick: (String) -> Unit,
    isTablet: Boolean,
    onDeviceDetailsClick: (id: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Title(text = stringResource(R.string.bottom_navigation_devices))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            if (devices.isEmpty()) {
                Text(text = stringResource(R.string.no_devices_message))
            } else {
                CardGridDev(
                    devices,
                    if(!isTablet){onDeviceClick}else{ null },
                    isTablet,
                    onDeviceDetailsClick
                )
            }
        }
    }
}

@Composable
fun CardGridDev(
    devices: List<DeviceResult>,
    onDeviceClick: ((String) -> Unit)? = null,
    isTablet: Boolean,
    onDeviceDetailsClick: (id: String) -> Unit
) {
    val colAmount = if(isTablet){ 4 }else{ 2 }
    LazyVerticalGrid(
        columns = GridCells.Fixed(colAmount), // Define the number of columns
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        itemsIndexed(
            devices,
            key = { _, item -> item.id }
        ) { _, device ->
            DeviceCard(
                name = device.name,
                state = device.state,
                type = device.type.name,
                onClick = {
                    onDeviceDetailsClick(device.id)
                    println("Clicked on ${device.id} AKA ${device.name}")
                    onDeviceClick?.invoke(device.id)
                },
                isTablet = isTablet
            )
        }
    }
}
