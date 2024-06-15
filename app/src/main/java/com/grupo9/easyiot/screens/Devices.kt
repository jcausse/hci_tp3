package com.grupo9.easyiot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grupo9.easyiot.DeviceCard
import com.grupo9.easyiot.R
import com.grupo9.easyiot.model.device.DeviceResult
import com.grupo9.easyiot.model.device.State
import com.grupo9.easyiot.model.device.Type
import com.grupo9.easyiot.model.routines.Meta

val devices = listOf(
    // Example devices based on the provided JSON data.
    DeviceResult(
        id = "066643a46c8b7579",
        name = "adasda",
        type = Type(id = "go46xmbqeomjrsjr", name = "lamp", powerUsage = 15),
        state = State.LampState(status = "off", color = "00FF00", brightness = 20),
        meta = Meta(description = "No house", weekdays = "No room")
    ),
    DeviceResult(
        id = "5837e303e602e901",
        name = "hgjhgj",
        type = Type(id = "lsf78ly0eqrjbz91", name = "door", powerUsage = 350),
        state = State.DoorState(status = "closed", lock = "locked"),
        meta = Meta(description = "No house", weekdays = "No room")
    ),
    // Add other devices based on the provided JSON data...
)



val kodchasan = FontFamily(
    Font(R.font.kodchasan_regular, FontWeight.Normal),
    Font(R.font.kodchasan_bold, FontWeight.Bold),
)

@Preview(showBackground = true)
@Composable
fun DevicesScreen(
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        Title(text = "Devices")
        CardGridDev()
    }
}
@Composable
fun CardGridDev() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Define the number of columns
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        itemsIndexed(devices) { index, device ->
            DeviceCard(name = device.name, type = device.type.name, idx = index+1,
                onClick = {
                // Handle the click event here
                    // funcion que lleva al expanded device view
                    // y carga el state
                println("Clicked on ${device.name}")
            })
        }
    }
}