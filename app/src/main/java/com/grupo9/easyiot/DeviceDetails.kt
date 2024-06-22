package com.grupo9.easyiot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.State
import com.grupo9.easyiot.ui.theme.Purple40
import com.grupo9.easyiot.ui.theme.Purple80

@Composable
fun DeviceDetails(
    device: DeviceResult,
    onExecuteAction: (Int, String) -> Unit,
    onChangeStatus: (Boolean) -> Unit,
    onExecuteActionWithoutParameter: (String) -> Unit
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
            SpeakerDetails(
                state,
                onExecuteAction,
                onExecuteActionWithoutParameter,
                onChangeStatus
            )
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
    var lightColor by remember { mutableStateOf(state.color) }
    Column {
        StateSwitch(
            status = (state.status == "on"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
            )
        DeviceSlider(
            state.brightness,
            stringResource(R.string.lamp_brightness),
            "%",
            { value -> onExecuteAction(value, "setBrightness")}
        )
        // TODO: ChangeOnExecute Action --> color needs to be a string
        /*
        ClassicColorPicker(
            color = Color,
            onColorChanged = { color: HsvColor ->
                lightColor = color.toString()
                onExecuteAction(lightColor.toInt(), "setColor")
            }
        )
        */
    }
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
            status = (state.status == "open"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
        )
        Lock()
    }
}

@Composable
fun Lock(
    state: State.DoorState,
    onTriggerLock: (Boolean) -> Unit
) {
    var locked by remember { mutableStateOf(state.lock == "locked")}

    Row (
        modifier = Modifier
        .padding(25.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "State: ${if(locked){"Locked"}else{"Unlocked"}}",
            fontSize = 20.sp,
            modifier = Modifier
        )
        Column {
            Button(
                onClick = {
                    locked = !locked
                    onTriggerLock(locked)
                },
                modifier = Modifier.padding(8.dp),
                colors = if (locked) {
                    ButtonDefaults.buttonColors(Color.Green)
                } else {
                    ButtonDefaults.buttonColors(Color.Red)
                }
            ) {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = "Lock Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
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
        DeviceSlider(
            state.level,
            stringResource(R.string.blinds_level),
            "%",
            { value -> onExecuteAction(value, "setLevel")}
        )
    }
}

//***************************************************************************//
//                                 Speaker                                   //
//***************************************************************************//
@Composable
fun SpeakerDetails(
    state: State.SpeakerState,
    onExecuteAction: (Int, String) -> Unit,
    onExecuteActionWithoutParameter: (String) -> Unit,
    onChangeStatus: (Boolean) -> Unit
) {
    Column {
        Song(
            state.song.title,
            parseTimeString(state.song.duration),
            parseTimeString(state.song.progress),
            onExecuteActionWithoutParameter,
            onChangeStatus
        )
        DeviceSlider(
            state.volume,
            stringResource(R.string.speaker_volume),
            "%",
            { value -> onExecuteAction(value, "setVolume") }
        )
        Column (
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Artist: ${state.song.artist}")
            Text("Album: ${state.song.album}")
            Text("Genre: ${state.genre}")
        }
    }
}

fun parseTimeString(timeString: String): Int {
    val parts = timeString.split(":")
    if (parts.size != 2) {
        throw NumberFormatException("Invalid time format")
    }
    val minutes = parts[0].toIntOrNull() ?: 0
    val seconds = parts[1].toIntOrNull() ?: 0
    return minutes * 60 + seconds
}

@Composable
fun Song(
    title: String,
    duration: Int,
    progress: Int,
    onExecuteActionWithoutParameter: (String) -> Unit,
    onChangeStatus: (Boolean) -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }
    var currentSong by remember { mutableStateOf(title) }
    var sliderPosition by remember { mutableStateOf(progress) }

    Column (
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .background(
                color = com.grupo9.easyiot.ui.theme.Purple120,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Title: ${currentSong}",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 13.dp)
        )
        Slider(
            value = sliderPosition.toFloat(),
            onValueChange = {
                sliderPosition = it.toInt()
            },
            valueRange = 0f..duration.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = Purple40,
                activeTrackColor = Purple40,
                inactiveTrackColor = Purple80
            ),
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.skip_previous),
                contentDescription = "Previous Song",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onExecuteActionWithoutParameter("previousSong")
                    }
            )
            Icon(
                painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
                contentDescription = if (isPlaying) "Pause" else "Play",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        isPlaying = !isPlaying
                        onChangeStatus(isPlaying)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.skip_next),
                contentDescription = "Next Song",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onExecuteActionWithoutParameter("nextSong") }
            )
        }
    }
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
        DeviceSlider(
            state.temperature,
            stringResource(R.string.temperature),
            " Cº",
            { value -> onExecuteAction(value, "setTemperature")})
        DeviceSlider(
            state.freezerTemperature,
            stringResource(R.string.freezer_temperature),
            " Cº",
            { value -> onExecuteAction(value, "setFreezerTemperature")}
        )
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
            status = (state.status == "open"),
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
        DropdownMenu(expanded = false, onDismissRequest = { /*TODO: Mode */ }) {
        }
        Slider(
            value = state.batteryLevel.toFloat(),
            onValueChange = { },
            enabled = false,
            valueRange = 0f .. 100f,
            steps = 5,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text("${(state.batteryLevel)}%")
        Text(
            "${stringResource(R.string.vacuum_location)}: ${state.location}",
            fontSize = 15.sp,
            modifier = Modifier // TODO: Modifier?
            )
    }
}

//***************************************************************************//

@Composable
fun ErrorExecutingAction(
    success: Boolean,
    onDismiss: () -> Unit
) {
    if (!success) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = { Text("Error executing action") },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun StateSwitch(
    status: Boolean,
    statusText: String,
    onChangeStatus: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(status) }

    Row (
        modifier = Modifier
            .padding(25.dp)
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
                    onChangeStatus(checked)
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
    symbol: String,
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
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            valueRange = 0f..100f,
            onValueChangeFinished = { onSliderChange(sliderPosition.toInt()) },
        )
        Text(text = "${(sliderPosition).toInt()}${symbol}")
    }
}




