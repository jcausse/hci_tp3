package com.grupo9.easyiot.model.routines

import kotlinx.serialization.SerialName


data class Type (

  @SerialName("id"         ) var id         : String,
  @SerialName("name"       ) var name       : String,
  @SerialName("powerUsage" ) var powerUsage : Int

)