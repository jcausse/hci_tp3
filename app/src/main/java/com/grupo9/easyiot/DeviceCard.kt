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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.easyiot.model.device.State
import java.util.Locale

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

private val spanishVersion= mapOf(
    "default" to "predeterminado",
    "vacation" to "vacaciones",
    "party" to "fiesta",
    "vacuum" to "aspiradora",
    "mop" to "fregar",
    "docked" to "atracado",
    "charging" to "cargando",
    "stopped" to "detenido",
    "cleaning" to "limpieza",
    "locked" to "bloqueado",
    "unlocked" to "desbloqueado",
    "opened" to "abierto",
    "closed" to "cerrado",
    "on" to "encendido",
    "off" to "apagado"
)

fun getSpanishOrDefault(word: String, spanish:Boolean): String {
    val toRet =
    if(!spanish) {
        word
    } else {
        spanishVersion[word] ?: word
    }
    return toRet.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}


fun truncateText(text: String, maxLength: Int): String {
    return if (text.length > maxLength) {
        text.substring(0, maxLength-3) + "..."
    } else {
        text
    }
}

@Composable
fun getDevStatusToStr(state: State, isTablet: Boolean): String {
    val baseString = StringBuilder()
    val isSpanish = stringResource(R.string.mode) == "Modo"

    when (state) {
        is State.RefrigeratorState -> {
            baseString.append(stringResource(R.string.temperature)).append(": ${state.temperature} C\n")
            .append(stringResource(R.string.freezer_temperature)).append(": ${state.freezerTemperature} C\n")
            if (isTablet) {
                baseString.append(stringResource(R.string.mode)).append(": ${getSpanishOrDefault(state.mode,isSpanish)}")
            }
        }
        is State.LampState -> {
            baseString.append(stringResource(R.string.default_status)).append(": ${getSpanishOrDefault(state.status,isSpanish)}\n")
            .append(stringResource(R.string.lamp_color)).append(": ${state.color}\n")
            if (isTablet) {
                baseString.append(stringResource(R.string.lamp_brightness)).append(": ${state.brightness}")
            }
        }
        is State.VacuumState -> {
            baseString.append(stringResource(R.string.default_status)).append(": ${getSpanishOrDefault(state.status,isSpanish)}\n")
            if (isTablet) {
                baseString.append(stringResource(R.string.mode)).append(": ${getSpanishOrDefault(state.mode,isSpanish)}\n")
                .append(stringResource(R.string.vacuum_battery_level)).append(": ${state.batteryLevel}%\n")
                .append(stringResource(R.string.vacuum_location)).append(": ${truncateText(state.location.name, 10)}")
            }
        }
        is State.FaucetState -> {
            baseString.append(stringResource(R.string.default_status)).append(": ${getSpanishOrDefault(state.status,isSpanish)}")
        }
        is State.DoorState -> {
            baseString.append(stringResource(R.string.default_status)).append(": ${getSpanishOrDefault(state.status,isSpanish)}\n")
            .append(stringResource(R.string.door_lock)).append(": ${getSpanishOrDefault(state.lock,isSpanish)}")
        }
        is State.SpeakerState -> {
            baseString.append(stringResource(R.string.default_status)).append(": ${getSpanishOrDefault(state.status,isSpanish)}\n")
            if (isTablet) {
                baseString.append(stringResource(R.string.speaker_song)).append(": ${truncateText(state.song.title, 30)}\n")
                .append(stringResource(R.string.speaker_artist)).append(": ${truncateText(state.song.artist, 16)}")
            }
        }
        is State.BlindsState -> {
            baseString.append(stringResource(R.string.default_status)).append(": ${getSpanishOrDefault(state.status,isSpanish)}\n")
            .append(stringResource(R.string.blinds_level)).append(": ${state.currentLevel}%")
        }
        is State.DefaultState -> {
            baseString.append(stringResource(R.string.default_status)).append(": ${getSpanishOrDefault(state.status,isSpanish)}")
        }
    }

    return baseString.toString()
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

            TitleDeviceCard(text = truncateText(name,dims.maxCharLen), isTablet)
            Icon(painter = painterResource( getDrawableForDeviceType(type)),
                contentDescription = "device",
                Modifier.size(dims.iconSize.dp),
                tint = MaterialTheme.colorScheme.primary )
            TextDeviceCard(text = getDevStatusToStr(state, isTablet), isTablet)
        }
    }
}

@Composable
fun TitleDeviceCard(text: String, isTablet: Boolean) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        fontSize = if(!isTablet){24}else{32}.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text,
        fontFamily = kodchasan,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun TextDeviceCard(text: String, isTablet: Boolean) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        fontSize = if(!isTablet){15}else{20}.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text,
        fontFamily = kodchasan,
        fontWeight = FontWeight.Normal
    )
}
