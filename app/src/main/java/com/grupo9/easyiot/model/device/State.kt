package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class State {
  @Serializable
  data class LampState(
    @SerialName("status") val status: String,
    @SerialName("color") val color: String,
    @SerialName("brightness") val brightness: Int
  ) : State()

  @Serializable
  data class DoorState(
    @SerialName("status") val status: String,
    @SerialName("lock") val lock: String
  ) : State()

  @Serializable
  data class BlindsState(
    @SerialName("status") val status: String,
    @SerialName("level") val level: Int,
    @SerialName("currentLevel") val currentLevel: Int
  ) : State()

  @Serializable
  data class SpeakerState(
    @SerialName("status") val status: String,
    @SerialName("volume") val volume: Int,
    @SerialName("genre") val genre: String,
    @SerialName("song") val song: Song = Song()
  ) : State() {
    @Serializable
    data class Song(
      @SerialName("title") val title: String = "Unknown",
      @SerialName("artist") val artist: String = "Unknown",
      @SerialName("album") val album: String = "Unknown",
      @SerialName("duration") val duration: String = "00:00",
      @SerialName("progress") val progress: String = "00:00"
    )
  }

  @Serializable
  data class RefrigeratorState(
    @SerialName("freezerTemperature") val freezerTemperature: Int,
    @SerialName("temperature") val temperature: Int,
    @SerialName("mode") val mode: String
  ) : State()

  @Serializable
  data class FaucetState(
    @SerialName("status") val status: String
  ) : State()

  @Serializable
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
