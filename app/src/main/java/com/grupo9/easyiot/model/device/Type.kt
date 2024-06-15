package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Type (

  @SerialName("id"         ) var id         : String,
  @SerialName("name"       ) var name       : String,
  @SerialName("powerUsage" ) var powerUsage : Int

)