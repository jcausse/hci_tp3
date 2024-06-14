package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName


data class State (

  @SerialName("status"     ) var status     : String,
  @SerialName("color"      ) var color      : String,
  @SerialName("brightness" ) var brightness : Int

)