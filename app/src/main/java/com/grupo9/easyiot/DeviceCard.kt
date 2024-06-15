package com.grupo9.easyiot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    "lamp" to R.drawable.lamp
    // Add more mappings as needed
)

// Method to get drawable resource ID based on device type
fun getDrawableForDeviceType(deviceType: String): Int {
    return deviceTypeToDrawable[deviceType] ?: R.drawable.file_question
}
@Composable
fun DeviceCard(name: String, type: String, idx: Int, onClick: () -> Unit ) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(10.dp)
            .width(160.dp)
            .height(160.dp)
            .clickable(onClick = onClick),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White,
            contentColor = MaterialTheme.colorScheme.primary),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            }
            Icon(painter = painterResource( getDrawableForDeviceType(type)),
                contentDescription = "device",
                Modifier.size(58.dp),
                tint = MaterialTheme.colorScheme.primary )
            TitleDeviceCard(text = name)
        }
    }
}

@Composable
fun TitleDeviceCard(text: String) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(5.dp),
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text,
        fontFamily = kodchasan,
        fontWeight = FontWeight.Normal
    )
}