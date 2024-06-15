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
import com.grupo9.easyiot.model.device.DeviceResultSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray

val jsonString = """
     [
    {
      "id": "066643a46c8b7579",
      "name": "adasda",
      "type": {
        "id": "go46xmbqeomjrsjr",
        "name": "lamp",
        "powerUsage": 15
      },
      "state": {
        "status": "off",
        "color": "00FF00",
        "brightness": 20
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "5837e303e602e901",
      "name": "hgjhgj",
      "type": {
        "id": "lsf78ly0eqrjbz91",
        "name": "door",
        "powerUsage": 350
      },
      "state": {
        "status": "closed",
        "lock": "locked"
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "6790ed0d1fa9feba",
      "name": "okjljkljl",
      "type": {
        "id": "lsf78ly0eqrjbz91",
        "name": "door",
        "powerUsage": 350
      },
      "state": {
        "status": "closed",
        "lock": "unlocked"
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "787056f70df70b3c",
      "name": "blinds 1",
      "type": {
        "id": "eu0v2xgprrhhg41g",
        "name": "blinds",
        "powerUsage": 350
      },
      "state": {
        "status": "opened",
        "level": 75,
        "currentLevel": 0
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "90c1351dd516e0e6",
      "name": "asdasd",
      "type": {
        "id": "c89b94e8581855bc",
        "name": "speaker",
        "powerUsage": 20
      },
      "state": {
        "status": "playing",
        "volume": 3,
        "genre": "classical",
        "song": {
          "title": "Piano Sonata No. 14 in C-Sharp Minor, Op. 27 No. 2",
          "artist": "Ludwig Van Beethoven, Louis Lortie",
          "album": "Beethoven: Complete Piano Sonatas",
          "duration": "7:22",
          "progress": "3:37"
        }
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "91cb466207273518",
      "name": "kopkopk",
      "type": {
        "id": "rnizejqr2di0okho",
        "name": "refrigerator",
        "powerUsage": 90
      },
      "state": {
        "freezerTemperature": -15,
        "temperature": 7,
        "mode": "vacation"
      },
      "room": {
        "id": "e052d06d575ad7fd",
        "name": "kjhknkm ",
        "home": {
          "id": "4cad1006ae058ab0",
          "name": "hkjk"
        }
      },
     "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "9cb38bd969eb0edd",
      "name": "asdas",
      "type": {
        "id": "dbrlsh7o5sn8ur4i",
        "name": "faucet",
        "powerUsage": 15
      },
      "state": {
        "status": "opened"
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "b31ee5b8c5f6fe00",
      "name": "dasdasd",
      "type": {
        "id": "ofglvd9gqx8yfl3l",
        "name": "vacuum",
        "powerUsage": 300
      },
      "state": {
        "status": "docked",
        "mode": "mop",
        "batteryLevel": 100,
        "location": {
          "id": "e052d06d575ad7fd",
          "name": "kjhknkm "
        }
      },
      "room": {
        "id": "e052d06d575ad7fd",
        "name": "kjhknkm ",
        "home": {
          "id": "4cad1006ae058ab0",
          "name": "hkjk"
        }
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "cccee25d8d6ce19f",
      "name": "door 1",
      "type": {
        "id": "lsf78ly0eqrjbz91",
        "name": "door",
        "powerUsage": 350
      },
      "state": {
        "status": "closed",
        "lock": "unlocked"
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    },
    {
      "id": "d066351aa624fdec",
      "name": "dfsdf",
      "type": {
        "id": "eu0v2xgprrhhg41g",
        "name": "blinds",
        "powerUsage": 350
      },
      "state": {
        "status": "closed",
        "level": 50,
        "currentLevel": 50
      },
      "meta": {
        "house": "No house",
        "room": "No room",
        "fav": true
      }
    }
  ]
"""
//mover a device api service
val jsonArray = Json.parseToJsonElement(jsonString).jsonArray



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

        //mover a device api service
        val devices = mutableListOf<DeviceResult>()
        for(element in jsonArray){
            devices.add(Json.decodeFromString(DeviceResultSerializer,element.toString()))
        }

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