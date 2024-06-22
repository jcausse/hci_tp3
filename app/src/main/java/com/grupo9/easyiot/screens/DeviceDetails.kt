package com.grupo9.easyiot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.grupo9.easyiot.DeviceDetailsCard
import com.grupo9.easyiot.model.device.DeviceResult

@Composable
fun DeviceDetailsScreen(deviceDetailsViewModel: DeviceDetailsViewModel) {

    when (val state = deviceDetailsViewModel.deviceDetailsState) {
        is DeviceDetailsState.Loading -> {
            LoadingScreen()
        }
        is DeviceDetailsState.Success -> {
            val result = state.get.result
            DeviceDetailsSuccessScreen(
                result,
                onBrightnessChange = { device, brightness -> deviceDetailsViewModel.changeBrightness(result, brightness) },
                onLevelChange = { device, level -> deviceDetailsViewModel.changeLevel(result, level) },
                onVolumeChange = { device, volume -> deviceDetailsViewModel.changeVolume(result, volume) },
                onChangeStatus = { device, status -> deviceDetailsViewModel.changeLampStatus(result, status) }
            )
        }
        is DeviceDetailsState.Error -> {
            ErrorScreen(state.message)
        }
    }
}

@Composable
fun DeviceDetailsSuccessScreen(
    device : DeviceResult,
    onBrightnessChange: (DeviceResult, Int) -> Unit,
    onLevelChange: (DeviceResult, Int) -> Unit,
    onVolumeChange: (DeviceResult, Int) -> Unit,
    onChangeStatus: (DeviceResult, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(text = device.name)
        DeviceDetailsCard(
            device,
            onBrightnessChange = { brightness -> onBrightnessChange(device, brightness) },
            onLevelChange = { level -> onLevelChange(device, level) },
            onVolumeChange = { volume -> onVolumeChange(device, volume) },
            onChangeStatus = { status -> onChangeStatus(device, status) },
        )
    }
}
