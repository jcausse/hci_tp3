package com.grupo9.easyiot.model.device

import com.grupo9.easyiot.model.routines.Meta
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* FOR TESTING-----------------------------
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
 ---------------------------------------*/

@Serializable
data class DeviceResult (
  @SerialName("id"    ) var id    : String,
  @SerialName("name"  ) var name  : String,
  @SerialName("type"  ) var type  : Type,
  @SerialName("state" ) var state : State,
  @SerialName("meta"  ) var meta  : Meta
)

/* TESTING --------------------
// Example JSON string
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
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
        "weekdays": "No house",
        "description": "No room"
      }
    }
  ]
"""

fun main() {
  // Example instances of DeviceResult
  val lampDevice = DeviceResult(
    "066643a46c8b7579",
    "adasda",
    Type("go46xmbqeomjrsjr", "lamp", 15),
    State.LampState("off", "00FF00", 20),
    Meta("No house", "No room")
  )

  val doorDevice = DeviceResult(
    "5837e303e602e901",
    "hgjhgj",
    Type("lsf78ly0eqrjbz91", "door", 350),
    State.DoorState("closed", "locked"),
    Meta("No house", "No room")
  )

  // Serialize each device to JSON
  val lampJson = Json.encodeToString(DeviceResultSerializer, lampDevice)
  val doorJson = Json.encodeToString(DeviceResultSerializer, doorDevice)

  println("Serialized Lamp Device: $lampJson")
  println("Serialized Door Device: $doorJson")

  try {

    val jsonArray = Json.parseToJsonElement(jsonString).jsonArray

    // Iterate through the array elements
    for (element in jsonArray) {
      if (element is JsonObject) {
        val deviceResult = Json.decodeFromString(DeviceResultSerializer,element.toString())

        // If parsing succeeds, print the deserialized object
       println("Parsed DeviceResult: $deviceResult")
      }
    }
    // Handle other validations or operations based on the parsed object
  } catch (e: Exception) {
    // Handle any exceptions that may occur during parsing
    println("Error parsing JSON: ${e.message}")
  }
}

 */