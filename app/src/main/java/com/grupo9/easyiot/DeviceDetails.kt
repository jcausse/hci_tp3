package com.grupo9.easyiot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.State
import kotlin.math.round

@Composable
fun DeviceDetails(
    device: DeviceResult,
    onExecuteAction: (Int, String) -> Unit,
    onChangeStatus: (Boolean) -> Unit
) {
    when (val state = device.state) {
        is State.LampState -> {
            LampDetails(state, onExecuteAction, onChangeStatus)
        }
        is State.DoorState -> {
            DoorDetails(state, onChangeStatus)
        }
        is State.BlindsState -> {
            BlindsDetails(state, onExecuteAction, onChangeStatus)
        }
        is State.SpeakerState -> {
            SpeakerDetails(state, onExecuteAction, onChangeStatus)
        }
        is State.RefrigeratorState -> {
            RefrigeratorDetails(state, onExecuteAction)
        }
        is State.FaucetState -> {
            FaucetDetails(state, onChangeStatus)
        }
        is State.VacuumState -> {
            VacuumDetails(state, onChangeStatus)
        }
        else -> {}
    }
}

//***************************************************************************//
//                                    Lamp                                   //
//***************************************************************************//
@Composable
fun LampDetails(
    state: State.LampState,
    onExecuteAction: (Int, String) -> Unit,
    onChangeStatus: (Boolean) -> Unit,
) {
    Column {
        StateSwitch(
            status = (state.status == "on"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
            )
        DeviceSlider(state.brightness, stringResource(R.string.lamp_brightness), { value -> onExecuteAction(value, "setBrightness")})
    }
}
//***************************************************************************//
@Composable
fun ColorPicker() {
    // TODO: https://github.com/skydoves/colorpicker-compose
}

//***************************************************************************//
//                                    Door                                   //
//***************************************************************************//
@Composable
fun DoorDetails(
    state: State.DoorState,
    onChangeStatus: (Boolean) -> Unit
) {
    Column {
        StateSwitch(
            status = (state.status == "on"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
        )
        //    @SerialName("lock") val lock: String
    }
}

//***************************************************************************//
//                                   Blinds                                  //
//***************************************************************************//
@Composable
fun BlindsDetails(
    state: State.BlindsState,
    onExecuteAction: (Int, String) -> Unit,
    onChangeStatus: (Boolean) -> Unit
) {
    Column {
        StateSwitch(
            status = (state.status == "on"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
        )
        DeviceSlider(state.level, stringResource(R.string.blinds_level), { value -> onExecuteAction(value, "setLevel")})
    }
}

//***************************************************************************//
//                                 Speaker                                   //
//***************************************************************************//
@Composable
fun SpeakerDetails(
    state: State.SpeakerState,
    onExecuteAction: (Int, String) -> Unit,
    onChangeStatus: (Boolean) -> Unit
) {
    Column {
        StateSwitch(
            status = (state.status == "on"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
        )
        DeviceSlider(state.volume, stringResource(R.string.speaker_volume), { value -> onExecuteAction(value, "setVolume")})
        Text("Genre: ${state.genre}")
        Text("Song: ${state.song}")
    }
    // Song
//      @SerialName("title") val title: String,
//      @SerialName("artist") val artist: String,
//      @SerialName("album") val album: String,
//      @SerialName("duration") val duration: String,
//      @SerialName("progress") val progress: String
}

//***************************************************************************//
//                               Refrigerator                                //
//***************************************************************************//
@Composable
fun RefrigeratorDetails(
    state: State.RefrigeratorState,
    onExecuteAction: (Int, String) -> Unit,
) {
    Column {
        DeviceSlider(state.temperature, stringResource(R.string.temperature), { value -> onExecuteAction(value, "setTemperature")})
        DeviceSlider(state.freezerTemperature, stringResource(R.string.freezer_temperature), { value -> onExecuteAction(value, "setFreezerTemperature")})
        //    @SerialName("mode") val mode: String
    }
}

//***************************************************************************//
//                                  Faucet                                   //
//***************************************************************************//
@Composable
fun FaucetDetails(
    state: State.FaucetState,
    onChangeStatus: (Boolean) -> Unit
) {
    Column {
        StateSwitch(
            status = (state.status == "on"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
        )
    }
}

//***************************************************************************//
//                                  Vacuum                                   //
//***************************************************************************//
@Composable
fun VacuumDetails(
    state: State.VacuumState,
    onChangeStatus: (Boolean) -> Unit
) {
    Column {
        StateSwitch(
            status = (state.status == "active"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
        )
        // TODO: Mode
        Slider(
            value = state.batteryLevel.toFloat(),
            onValueChange = { },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text("${(state.batteryLevel)}%")
        // TODO: Location
    }
}


/* TODO: @Composable
fun ChangeDevice(id: String, name: String, onDismiss: () -> Unit, routinesViewModel: RoutinesViewModel = RoutinesViewModel()) {

    routinesViewModel.executeRoutine(id)
    val executeResult  = routinesViewModel.executeResult

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(name) },
        text = { Text(if ( executeResult ) "Executed Routine Successfully" else "Error executing routine") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
} */


@Composable
fun StateSwitch(
    status: Boolean,
    statusText: String,
    onChangeStatus: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(status) }
    Row (
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Status: ",
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onChangeStatus(it)
                }
            )
            Text(statusText)
        }
    }
}

@Composable
fun DeviceSlider(
    value: Int,
    valueText: String,
    onSliderChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(value.toFloat()) }
    Column(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxWidth()
    ) {
        Text(
            "${valueText}: ",
            fontSize = 20.sp
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            valueRange = 0f..100f,
            onValueChangeFinished = { onSliderChange(sliderPosition.toInt()) }
        )
        Text(text = "${(sliderPosition).toInt()}%")
    }
}




