package com.grupo9.easyiot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.easyiot.model.device.State

val kodchasan = FontFamily(
    Font(R.font.kodchasan_regular, FontWeight.Normal),
    Font(R.font.kodchasan_bold, FontWeight.Bold),
)

private val deviceTypeToDrawable = mapOf(
    "refrigerator" to R.drawable.fridge,
    "faucet" to R.drawable.faucet,
    "door" to R.drawable.door,
    "blinds" to R.drawable.blinds,
    "speaker" to R.drawable.speaker,
    "vacuum" to R.drawable.vacuum,
    "lamp" to R.drawable.lamp,
    "default" to R.drawable.file_question
    // Add more mappings as needed
)

// Method to get drawable resource ID based on device type
fun getDrawableForDeviceType(deviceType: String): Int {
    return deviceTypeToDrawable[deviceType] ?: R.drawable.file_question
}

fun truncateText(text: String, maxLength: Int): String {
    return if (text.length > maxLength) {
        text.substring(0, maxLength-3) + "..."
    } else {
        text
    }
}

fun getDevStatusToStr(state: State): String {
    return when (state) {
        is State.RefrigeratorState -> {
            "Temp: ${state.temperature} C\nFreezeTemp: ${state.freezerTemperature} C"
        }
        is State.LampState -> {
            "Status: ${state.status}\nColor: ${state.color}"
        }
        is State.VacuumState -> {
            "Status: ${state.status}"
        }
        is State.FaucetState -> {
            "Status: ${state.status}"
        }
        is State.DoorState -> {
            "Status: ${state.status}\nLock: ${state.lock}"
        }
        is State.SpeakerState -> {
            "Status: ${state.status}"
        }
        is State.BlindsState -> {
            "Status: ${state.status}"
        }
        is State.DefaultState -> {
            "Status: ${state.status}"
        }
    }
}


data class Dimensions(
    val padding: Int,
    val width: Int,
    val height: Int,
    val iconSize: Int,
    val maxCharLen : Int
)

fun dimensionType(isTablet: Boolean) : Dimensions{
    return if(!isTablet){
        Dimensions(10,165,180,58, 11)
    } else {
        Dimensions(10,165,240,80, 20)
    }
}

@Composable
fun DeviceCard(name: String, type: String, state: State, onClick: () -> Unit, isTablet: Boolean ) {

    val dims = dimensionType(isTablet)

    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(dims.padding.dp)
            .width(dims.width.dp)
            .height(dims.height.dp)
            .clickable(onClick = onClick),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White,
            contentColor = MaterialTheme.colorScheme.primary),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(dims.padding.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {

            TitleDeviceCard(text = truncateText(name,dims.maxCharLen))
            Icon(painter = painterResource( getDrawableForDeviceType(type)),
                contentDescription = "device",
                Modifier.size(dims.iconSize.dp),
                tint = MaterialTheme.colorScheme.primary )
            TextDeviceCard(text = getDevStatusToStr(state))
        }
    }
}

@Composable
fun TitleDeviceCard(text: String) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text,
        fontFamily = kodchasan,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun TextDeviceCard(text: String) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text,
        fontFamily = kodchasan,
        fontWeight = FontWeight.Normal
    )
}
