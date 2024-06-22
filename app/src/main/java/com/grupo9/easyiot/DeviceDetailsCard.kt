package com.grupo9.easyiot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.grupo9.easyiot.model.device.DeviceResult

@Composable
fun DeviceDetailsCard(
    device : DeviceResult,
    onExecuteAction: (Int, String) -> Unit,
    onExecuteActionWithoutParameter: (String) -> Unit,
    onChangeStatus: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(580.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DeviceIcon(device.type.name, 45.dp, 55.dp, 108.dp)
            DeviceDetails(
                device,
                onExecuteAction,
                onChangeStatus,
                onExecuteActionWithoutParameter
            )
        }
    }
}
