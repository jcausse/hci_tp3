package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class State {
  @Serializable
  @SerialName("lamp")
  data class LampState(
    @SerialName("status") val status: String,
    @SerialName("color") val color: String,
    @SerialName("brightness") val brightness: Int
  ) : State()

  @Serializable
  @SerialName("door")
  data class DoorState(
    @SerialName("status") val status: String,
    @SerialName("lock") val lock: String
  ) : State()

  @Serializable
  @SerialName("blinds")
  data class BlindsState(
    @SerialName("status") val status: String,
    @SerialName("level") val level: Int,
    @SerialName("currentLevel") val currentLevel: Int
  ) : State()

  @Serializable
  @SerialName("speaker")
  data class SpeakerState(
    @SerialName("status") val status: String,
    @SerialName("volume") val volume: Int,
    @SerialName("genre") val genre: String,
    @SerialName("song") val song: Song
  ) : State() {
    @Serializable
    data class Song(
      @SerialName("title") val title: String,
      @SerialName("artist") val artist: String,
      @SerialName("album") val album: String,
      @SerialName("duration") val duration: String,
      @SerialName("progress") val progress: String
    )
  }

  @Serializable
  @SerialName("refrigerator")
  data class RefrigeratorState(
    @SerialName("freezerTemperature") val freezerTemperature: Int,
    @SerialName("temperature") val temperature: Int,
    @SerialName("mode") val mode: String
  ) : State()

  @Serializable
  @SerialName("faucet")
  data class FaucetState(
    @SerialName("status") val status: String
  ) : State()

  @Serializable
  @SerialName("vacuum")
  data class VacuumState(
    @SerialName("status") val status: String,
    @SerialName("mode") val mode: String,
    @SerialName("batteryLevel") val batteryLevel: Int,
    @SerialName("location") val location: Location
  ) : State() {
    @Serializable
    data class Location(
      @SerialName("id") val id: String,
      @SerialName("name") val name: String
    )
  }
}
