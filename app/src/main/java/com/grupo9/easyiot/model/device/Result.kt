package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName

data class DeviceResult (

  @SerialName("id"    ) var id    : String? = null,
  @SerialName("name"  ) var name  : String? = null,
  @SerialName("type"  ) var type  : Type?   = Type(),
  @SerialName("state" ) var state : State?  = State()
  //@SerialName("meta"  ) var meta  : Meta?   = Meta()

)