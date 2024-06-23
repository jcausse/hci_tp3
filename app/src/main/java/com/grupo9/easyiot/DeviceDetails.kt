package com.grupo9.easyiot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.decapitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.State
import com.grupo9.easyiot.ui.theme.Purple120
import com.grupo9.easyiot.ui.theme.Purple40
import com.grupo9.easyiot.ui.theme.Purple80

@Composable
fun DeviceDetails(
    device: DeviceResult,
    onExecuteAction: (Any, String) -> Unit,
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
            VacuumDetails(state, onChangeStatus, onExecuteAction)
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
    onExecuteAction: (Any, String) -> Unit,
    onChangeStatus: (Boolean) -> Unit,
) {
    var color by remember { mutableStateOf(state.color) }

    Column {
        StateSwitch(
            status = (state.status == "on"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
            )
        DeviceSlider(
            state.brightness,
            stringResource(R.string.lamp_brightness),
            0f,
            100f,
            "%",
            { value -> onExecuteAction(value, "setBrightness")}
        )
        ColorsDropDown(
            selectedValue = color,
            options = listOf(
                Color.White,
                Color.Yellow,
                Color.Green,
                Color.Cyan,
                Color.Blue
            ),
            { newColor -> color = hexToColor[newColor] ?: "#FFFFFFFF"},
            { onExecuteAction(color, "setColor") } // TODO: Change to the correct call
        )
    }
}

private val colorToHex = mapOf(
    "#FFFFFFFF" to Color.White,
    "#FFFFFF00" to Color.Yellow,
    "#FF00FF00" to Color.Green,
    "#FF00FFFF" to Color.Cyan,
    "#FF0000FF" to Color.Blue
)

private val hexToColor = mapOf(
    Color.White to "#FFFFFFFF",
    Color.Yellow to "#FFFFFF00",
    Color.Green to "#FF00FF00",
    Color.Cyan to "#FF00FFFF",
    Color.Blue to "#FF0000FF"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorsDropDown(
    selectedValue: String,
    options: List<Color>,
    onValueChange: (Color) -> Unit,
    onExecuteAction: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(Modifier.fillMaxWidth().padding(10.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.background(color = colorToHex[selectedValue] ?: Color.White).size(50.dp).padding(end = 5.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedValue,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option: Color ->
                    DropdownMenuItem(
                        text = {},
                        onClick = {
                            expanded = false
                            onValueChange(option)
                            onExecuteAction(hexToColor[option] ?: "#FFFFFFFF")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(option)
                    )
                }
            }
        }
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
            status = (state.status == "opened"),
            statusText = state.status,
            onChangeStatus = onChangeStatus
        )
        Lock(state, onChangeStatus)
    }
}

@Composable
fun Lock(
    state: State.DoorState,
    onTriggerLock: (Boolean) -> Unit
) {
    var locked by remember(state.lock) { mutableStateOf(state.lock == "locked")}

    Row(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "${stringResource(R.string.default_status)}: ${if(locked){stringResource(R.string.door_locked)}else{stringResource(R.string.door_unlocked)}}",
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
                    contentDescription = "Lock Icon", // TODO
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
    onExecuteAction: (Any, String) -> Unit,
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
            0f,
            100f,
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
    onExecuteAction: (Any, String) -> Unit,
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
            0f,
            100f,
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

@Composable
fun Song(
    title: String,
    duration: Int,
    progress: Int,
    onExecuteActionWithoutParameter: (String) -> Unit,
    onChangeStatus: (Boolean) -> Unit,
) {
    var isPlaying by remember { mutableStateOf(false) }
    var currentSong by remember { mutableStateOf(title) }
    var sliderPosition by remember { mutableStateOf(progress) }

    Column (
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .background(
                color = Purple120,
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
                contentDescription = stringResource(R.string.speaker_previous_song),
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onExecuteActionWithoutParameter("previousSong")
                    }
            )
            Icon(
                painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
                contentDescription = if (isPlaying) stringResource(R.string.speaker_pause) else stringResource(R.string.speaker_play),
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        isPlaying = !isPlaying
                        onChangeStatus(isPlaying)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.skip_next),
                contentDescription = stringResource(R.string.speaker_next_song),
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onExecuteActionWithoutParameter("nextSong") }
            )
        }
    }
    Icon(
        painter = painterResource(R.drawable.stop),
        contentDescription = stringResource(R.string.speaker_stop),
        modifier = Modifier
            .size(40.dp)
            .clickable {
                onExecuteActionWithoutParameter("stop")
            }
    )
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

private val modemap = mapOf(
    "Vaciones" to "vacations",
    "Predeterminado" to "default",
    "Fiesta" to "party",
    "Vacations" to "vacations",
    "Default" to "default",
    "Party" to "party",
)
//***************************************************************************//
//                               Refrigerator                                //
//***************************************************************************//
@Composable
fun RefrigeratorDetails(
    state: State.RefrigeratorState,
    onExecuteAction: (Any, String) -> Unit,
) {
    val isSpanish = stringResource(R.string.mode) == "Modo"

    Column {
        DeviceSlider(
            state.temperature,
            stringResource(R.string.temperature),
            2f,
            8f,
            " Cº",
            { value -> onExecuteAction(value, "setTemperature")})
        DeviceSlider(
            state.freezerTemperature,
            stringResource(R.string.freezer_temperature),
            -20f,
            -8f,
            " Cº",
            { value -> onExecuteAction(value, "setFreezerTemperature")}
        )
        var selectedOption by remember { mutableStateOf(getSpanishOrDefault(state.mode, isSpanish)) }

        DropDown(
            selectedOption,
            listOf(
                stringResource(R.string.default_mode),
                stringResource(R.string.vacation_mode),
                stringResource(R.string.party_mode),
                ),
            { newValue -> selectedOption = newValue },
            { onExecuteAction(modemap[selectedOption] ?: "default", "setMode") }
        )
    }
}

// https://medium.com/@german220291/building-a-custom-exposed-dropdown-menu-with-jetpack-compose-d65232535bf2
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(
    selectedValue: String,
    options: List<String>,
    onValueChange: (String) -> Unit,
    onExecuteAction: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChange(option)
                        onExecuteAction(option)
                    }
                )
            }
        }
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
            status = (state.status == "opened"),
            statusText = state.status, // Text will be translated (if necessary) in StateSwitch
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
    onChangeStatus: (Boolean) -> Unit,
    onExecuteAction: (Int, String) -> Unit
) {
    val isSpanish = stringResource(R.string.mode) == "Modo"

    Column {
        StateSwitch(
            status = (state.status == "active"),
            statusText = state.status, // Text will be translated (if necessary) in StateSwitch
            onChangeStatus = onChangeStatus
        )
        var selectedOption by remember { mutableStateOf(getSpanishOrDefault(state.mode, isSpanish)) }

        DropDown(
            selectedOption,
            listOf(
                stringResource(R.string.vacuum_vacuum),
                stringResource(R.string.vacuum_mop),
            ),
            { newValue -> selectedOption = newValue },
            { onExecuteAction(1, "setMode") }
        )

        Slider(
            value = state.batteryLevel.toFloat(),
            onValueChange = { },
            enabled = false,
            valueRange = 0f .. 100f,
            steps = 5,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
        Text("${(state.batteryLevel)}%")
        Text(
            "${stringResource(R.string.vacuum_location)}: ${state.location}",
            fontSize = 15.sp)
    }
}

//***************************************************************************//

@Composable
fun StateSwitch(
    status: Boolean,
    statusText: String,
    onChangeStatus: (Boolean) -> Unit
) {
    val isSpanish = stringResource(R.string.mode) == "Modo"
    var checked by remember { mutableStateOf(status) }

    Row (
        modifier = Modifier
            .padding(25.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "${stringResource(R.string.default_status)}: ",
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
            Text(getSpanishOrDefault(statusText, isSpanish))
        }
    }
}

@Composable
fun DeviceSlider(
    value: Int,
    valueText: String,
    minValue: Float,
    maxValue: Float,
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
            valueRange = minValue..maxValue,
            onValueChangeFinished = { onSliderChange(sliderPosition.toInt()) },
        )
        Text(text = "${(sliderPosition).toInt()}${symbol}")
    }
}




