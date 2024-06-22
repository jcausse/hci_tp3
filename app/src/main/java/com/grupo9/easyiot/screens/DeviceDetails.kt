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
                device = result,
                onExecuteAction = { device, value, action
                    -> deviceDetailsViewModel.executeAction(result.id, action, value) },
                onChangeStatus = { device, status
                    -> deviceDetailsViewModel.changeStatus(result, status) }
            )
        }
        is DeviceDetailsState.Error -> {
            ErrorScreen()
        }
    }
}

@Composable
fun DeviceDetailsSuccessScreen(
    device : DeviceResult,
    onExecuteAction: (DeviceResult, Int, String) -> Unit,
    onChangeStatus: (DeviceResult, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(text = device.name)
        DeviceDetailsCard(
            device = device,
            onExecuteAction = { value, action -> onExecuteAction(device, value, action) },
            onChangeStatus = { status -> onChangeStatus(device, status) },
        )
    }
}
