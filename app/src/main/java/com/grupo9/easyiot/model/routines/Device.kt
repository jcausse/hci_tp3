package com.grupo9.easyiot.model.routines

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Device (

  @SerialName("id"   ) var id   : String,
  @SerialName("name" ) var name : String,
  @SerialName("type" ) var type : Type,
  @SerialName("routinemeta" ) var meta : Meta

)