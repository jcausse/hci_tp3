package com.grupo9.easyiot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.grupo9.easyiot.DeviceCard
import com.grupo9.easyiot.R
import com.grupo9.easyiot.model.device.DeviceResult

val kodchasan = FontFamily(
    Font(R.font.kodchasan_regular, FontWeight.Normal),
    Font(R.font.kodchasan_bold, FontWeight.Bold),
)

@Composable
fun DevicesScreen( devicesState: DevicesState, onDeviceClick: ((String) -> Unit), isTablet: Boolean){
    when ( devicesState ){
        is DevicesState.Loading -> {
            LoadingScreen()
        }
        is DevicesState.Success -> {
            SuccessScreen(devicesState.get.result, onDeviceClick, isTablet)
        }
        is DevicesState.Error -> {
            ErrorScreen(devicesState.message)
        }
    }
}

@Composable
fun LoadingScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        Text(text =  "loading...")
    }
}
@Composable
fun ErrorScreen(message: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        Text(text =  message)
    }
}
@Composable
fun SuccessScreen(devices : ArrayList<DeviceResult>, onDeviceClick: (String) -> Unit, isTablet: Boolean){
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Title(text = "Devices")
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            if (devices.isEmpty()){
                Text(text = "Devices will appear here as you add them via the website.")
            }
            else{
                CardGridDev(devices, if(!isTablet){onDeviceClick}else{null},isTablet)
            }
        }
    }
}
@Composable
fun CardGridDev(devices: List<DeviceResult>, onDeviceClick: ((String) -> Unit)? = null, isTablet: Boolean) {
    val colAmount = if(isTablet){
        4
    } else {
        2
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(colAmount), // Define the number of columns
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        itemsIndexed(
            devices,
            key = { _, item -> item.id }
        )
        { _, device ->
            DeviceCard(name = device.name, state = device.state, type = device.type.name,
                onClick = {
                    // Handle the click event here
                    // funcion que lleva al expanded device view
                    // y carga el state
                    println("Clicked on ${device.id} AKA ${device.name}")
                    onDeviceClick?.invoke(device.id)
                }, isTablet = isTablet
            )
        }
    }
}
