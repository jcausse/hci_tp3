package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName


data class Type (

  @SerialName("id"         ) var id         : String? = null,
  @SerialName("name"       ) var name       : String? = null,
  @SerialName("powerUsage" ) var powerUsage : Int?    = null

)