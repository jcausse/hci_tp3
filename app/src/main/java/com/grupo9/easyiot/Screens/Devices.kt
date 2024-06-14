package com.grupo9.easyiot.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.easyiot.R


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
        TitleDev(text = "Devices")
        CardGridDev()
    }
}
@Composable
fun DeviceCard(name: String, time: String, description: String) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(10.dp)
            .width(160.dp)
            .height(160.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White, contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            androidx.compose.foundation.layout.Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            }
            androidx.compose.material3.Text(
                modifier = Modifier.padding(5.dp),
                text = description)
            androidx.compose.material3.Text(text = time, Modifier.align(Alignment.End))
        }
    }
}

@Composable
fun TitleDev(text: String) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(5.dp),
        fontSize = 64.sp,
        color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
        text = text,
        fontFamily = kodchasan,
        fontWeight = FontWeight.Bold
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardGridDev() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Define the number of columns
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items((1..20).toList()) { index ->
            DeviceCard(name = "A mimir", time = "LMMJVSD", description = "Zzzzzz")
        }
    }
}