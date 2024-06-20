package com.grupo9.easyiot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.grupo9.easyiot.getDrawableForDeviceType
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.Meta
import com.grupo9.easyiot.model.device.State
import com.grupo9.easyiot.model.device.Type

// TODO: This is a new screen afak, that
// TODO: Question, do I need to get the state of the devices again when i already have the list from
//  the prev screen? What about when I update the state (communication with the api (UI --> API)?

@Composable
fun DeviceDetailsScreen(deviceDetailsViewModel: DeviceDetailsViewModel) {
    val state = deviceDetailsViewModel.deviceDetailsState
    when (state) {
        is DeviceDetailsState.Loading -> {
            LoadingScreen()
        }
        is DeviceDetailsState.Success -> {
            DeviceDetailsSuccessScreen(state.get.result)
        }
        is DeviceDetailsState.Error -> {
            ErrorScreen(state.message)
        }
    }
}

// TODO: RECOMPOSITION!! "Any time a state is updated a recomposition takes place."
@Composable
fun DeviceDetailsSuccessScreen(device : DeviceResult) {
    // TODO: Adjust/Layout
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: Here instead of "Devices", I need the name of the device
        Title(text = "$device.name")
        DetailsCard(device)
    }
}

@Composable
fun DetailsCard(device : DeviceResult) {
    // TODO: I probably dont need a grid, I just need the one card, centered.
    // Device icon/image
    Icon(painter = painterResource(getDrawableForDeviceType(device.type.name)),
        contentDescription = "device", // TODO: Mire is utal itt a device?
        Modifier.size(58.dp),
        tint = MaterialTheme.colorScheme.primary)

    // Device details/options
    when (device.state) { // TODO: Ha elötte már amúgy is átváltjuk string-re akkor már használhatnánk közv. azt
        is State.LampState -> {
            LampDetailsCard()
        }
        is State.DoorState -> {
            DoorDetailsCard()
        }
        is State.BlindsState -> {
            BlindsDetailsCard()
        }
        is State.SpeakerState -> {
            SpeakerDetailsCard()
        }
        is State.RefrigeratorState -> {
            RefrigeratorDetailsCard()
        }
        is State.FaucetState -> {
            FaucetDetailsCard()
        }
        is State.VacuumState -> {
            VacuumDetailsCard()
        }
        else -> {
            // TODO: What would be apropriate here?
        }
    }
}

// TODO: Check on recomposition (szia)
// TODO: Itt szerintem majd ezt használd: var ... by remember { mutableStateOf(...) }
@Composable
fun LampDetailsCard() {
    // Status
    // Color
    // Brightness
}

@Preview
@Composable
fun DoorDetailsCard() {
    // Status
    // Lock
}

@Preview
@Composable
fun BlindsDetailsCard() {
    // Status
    // Level
    // Current Level
}

@Composable
fun SpeakerDetailsCard() {
    // Status
    // Volume
    // Genre
    // Song
    // --> If a song is playing:
    // Title
    // Artist
    // Album
    // Duration
    // Progress
}

@Composable
fun RefrigeratorDetailsCard() {
    // Freezer Temp
    // Temperature
    // Mode
}

@Composable
fun FaucetDetailsCard() {
    // Status
}

@Composable
fun VacuumDetailsCard() {
    // Status
    // Mode
    // BatteryLevel
    // Location
}

@Composable
fun OnOffSwitch() {

}
