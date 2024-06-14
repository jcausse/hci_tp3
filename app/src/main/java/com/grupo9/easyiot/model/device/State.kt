package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName


data class State (

  @SerialName("status"     ) var status     : String? = null,
  @SerialName("color"      ) var color      : String? = null,
  @SerialName("brightness" ) var brightness : Int?    = null

)