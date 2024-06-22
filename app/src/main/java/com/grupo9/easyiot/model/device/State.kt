package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class State {
  @Serializable
  data class LampState(
    @SerialName("status") val status: String = "Unknown",
    @SerialName("color") val color: String = "Unknown",
    @SerialName("brightness") val brightness: Int = 0
  ) : State()

  @Serializable
  data class DoorState(
    @SerialName("status") val status: String = "Unknown",
    @SerialName("lock") val lock: String = "Unknown"
  ) : State()

  @Serializable
  data class BlindsState(
    @SerialName("status") val status: String = "Unknown",
    @SerialName("level") val level: Int = 0,
    @SerialName("currentLevel") val currentLevel: Int = 0
  ) : State()

  @Serializable
  data class SpeakerState(
    @SerialName("status") val status: String = "Unknown",
    @SerialName("volume") val volume: Int = 0,
    @SerialName("genre") val genre: String = "Unknown",
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
    @SerialName("freezerTemperature") val freezerTemperature: Int = 100,
    @SerialName("temperature") val temperature: Int = 100,
    @SerialName("mode") val mode: String = "Unknown"
  ) : State()

  @Serializable
  data class FaucetState(
    @SerialName("status") val status: String = "Unknown"
  ) : State()

  @Serializable
  data class VacuumState(
    @SerialName("status") val status: String = "Unknown",
    @SerialName("mode") val mode: String = "Unknown",
    @SerialName("batteryLevel") val batteryLevel: Int = 0,
    @SerialName("location") val location: Location = Location()
  ) : State() {
    @Serializable
    data class Location(
      @SerialName("id") val id: String = "Unknown",
      @SerialName("name") val name: String = "Unknown"
    )
  }

  @Serializable
  data class DefaultState(
    @SerialName("UnknownState") val status: String = "Unknown",
  ) : State()
}
