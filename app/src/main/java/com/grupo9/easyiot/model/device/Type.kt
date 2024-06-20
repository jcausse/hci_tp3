package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Type (
  @SerialName("id"         ) var id         : String = "default",
  @SerialName("name"       ) var name       : String = "default",
  @SerialName("powerUsage" ) var powerUsage : Int = 0

)