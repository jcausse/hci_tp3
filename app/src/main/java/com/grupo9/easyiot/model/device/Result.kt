package com.grupo9.easyiot.model.device
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = DeviceResultSerializer::class)
data class DeviceResult (
  @SerialName("id"    ) var id    : String = "",
  @SerialName("name"  ) var name  : String = "",
  @SerialName("type"  ) var type  : Type = Type(),
  @SerialName("state" ) var state : State = State.DefaultState(),
  @SerialName("meta"  ) var meta  : Meta = Meta()
)
