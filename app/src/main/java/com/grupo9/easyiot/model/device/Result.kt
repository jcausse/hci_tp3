package com.grupo9.easyiot.model.device

import com.grupo9.easyiot.model.routines.Meta
import kotlinx.serialization.SerialName

data class DeviceResult (

  @SerialName("id"    ) var id    : String,
  @SerialName("name"  ) var name  : String,
  @SerialName("type"  ) var type  : Type,
  @SerialName("state" ) var state : State,
  @SerialName("meta"  ) var meta  : Meta

)