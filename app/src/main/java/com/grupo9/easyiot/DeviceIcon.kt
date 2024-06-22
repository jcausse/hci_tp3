package com.grupo9.easyiot

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun DeviceIcon(
    deviceType: String,
    paddingTop: Dp,
    paddingBottom: Dp,
    size: Dp
    ) {
    Icon(
        painter = painterResource(getDrawableForDeviceType(deviceType)),
        contentDescription = "device",
        Modifier
            .padding(top = paddingTop, bottom = paddingBottom)
            .size(size),
        tint = MaterialTheme.colorScheme.primary
    )
}