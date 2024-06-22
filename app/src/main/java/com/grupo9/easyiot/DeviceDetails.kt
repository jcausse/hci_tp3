package com.grupo9.easyiot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.State
import kotlin.math.round

@Composable
fun DeviceDetails(
    device: DeviceResult,
    onBrightnessChange: (Int) -> Unit,
    onLevelChange: (Int) -> Unit,
    onVolumeChange: (Int) -> Unit
) {
    when (val state = device.state) {
        is State.LampState -> { LampDetails(state, onBrightnessChange) }
        is State.DoorState -> { DoorDetails(state) }
        is State.BlindsState -> { BlindsDetails(state, onLevelChange) }
        is State.SpeakerState -> { SpeakerDetails(state, onVolumeChange) }
        is State.RefrigeratorState -> { RefrigeratorDetails(state) }
        is State.FaucetState -> { FaucetDetails(state) }
        is State.VacuumState -> { VacuumDetails(state) }
    }
}

//***************************************************************************//
//                                    Lamp                                   //
//***************************************************************************//
@Composable
fun LampDetails(
    state: State.LampState,
    onBrightnessChange: (Int) -> Unit
) {
    Column {
        StateSwitch(turnedOn = (state.status == "On"))
        IntensitySlider(state.brightness, onBrightnessChange)
    }
}
//***************************************************************************//
@Composable
fun StateSwitch(turnedOn: Boolean) {
    var checked by remember { mutableStateOf(false) }
    //Column(
    //    modifier = Modifier
    //        .padding(20.dp)
    //) {
        Row (
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Status: ",
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = turnedOn,
                onCheckedChange = {
                    checked = it
                }
            )


        }
    //}
}

@Composable
fun IntensitySlider(
    brightness: Int,
    onBrightnessChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(brightness.toFloat()) }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Text(
            "Brightness: ",
            fontSize = 30.sp
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            onValueChangeFinished = { onBrightnessChange(sliderPosition.toInt()) }
        )
        // val brightnessPercentage = round(sliderPosition * 100)
        // Text(text = (brightnessPercentage).toString() + "%")
    }
}

//***************************************************************************//
//                                    Door                                   //
//***************************************************************************//
@Composable
fun DoorDetails(state: State.DoorState) {
    Column {
        StateSwitch(turnedOn = (state.status == "On"))
        //    @SerialName("lock") val lock: String
    }
}

//***************************************************************************//
//                                   Blinds                                  //
//***************************************************************************//
@Composable
fun BlindsDetails(
    state: State.BlindsState,
    onLevelChange: (Int) -> Unit
) {
    Column {
        StateSwitch(turnedOn = (state.status == "on"))
        BlindsLevelSlider(level = state.level, onLevelChange)
        //    @SerialName("level") val level: Int,
        //    @SerialName("currentLevel") val currentLevel: Int
    }
}
//***************************************************************************//
@Composable
fun BlindsLevelSlider(
    level: Int,
    onLevelChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(level.toFloat()) }
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Text(
            "Level: ",
            fontSize = 30.sp
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            onValueChangeFinished = { onLevelChange(sliderPosition.toInt()) }
        )
        // val brightnessPercentage = round(sliderPosition * 100)
        // Text(text = (brightnessPercentage).toString() + "%")
    }
}

//***************************************************************************//
//                                 Speaker                                   //
//***************************************************************************//
@Composable
fun SpeakerDetails(
    state: State.SpeakerState,
    onVolumeChange: (Int) -> Unit
) {
    Column {
        StateSwitch(turnedOn = (state.status == "on"))
        VolumeSlider(level = state.volume, onVolumeChange)
        Text("Genre: ${state.genre}")
        Text("Song: ${state.song}") // ???
    }
    // Song
//      @SerialName("title") val title: String,
//      @SerialName("artist") val artist: String,
//      @SerialName("album") val album: String,
//      @SerialName("duration") val duration: String,
//      @SerialName("progress") val progress: String
}
//***************************************************************************//
@Composable
fun VolumeSlider(
    level: Int,
    onVolumeChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(level.toFloat()) }
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Text(
            "Level: ",
            fontSize = 30.sp
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            onValueChangeFinished = { onVolumeChange(sliderPosition.toInt()) }
        )
        Text(text = round(sliderPosition).toString())
    }
}
//***************************************************************************//
//                               Refrigerator                                //
//***************************************************************************//
@Composable
fun RefrigeratorDetails(state: State.RefrigeratorState) {
//    @SerialName("freezerTemperature") val freezerTemperature: Int,
//    @SerialName("temperature") val temperature: Int,
//    @SerialName("mode") val mode: String
}

//***************************************************************************//
//                                  Faucet                                   //
//***************************************************************************//
@Composable
fun FaucetDetails(state: State.FaucetState) {
    Column {
        StateSwitch(turnedOn = (state.status == "on"))
    }
}

//***************************************************************************//
//                                  Vacuum                                   //
//***************************************************************************//
@Composable
fun VacuumDetails(state: State.VacuumState) {
    Column {
        StateSwitch(turnedOn = (state.status == "active"))
        //    @SerialName("mode") val mode: String,
        //    @SerialName("batteryLevel") val batteryLevel: Int,
        //    @SerialName("location") val location: Location
    }
//    data class Location(
//      @SerialName("id") val id: String,
//      @SerialName("name") val name: String

}


/* @Composable
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



