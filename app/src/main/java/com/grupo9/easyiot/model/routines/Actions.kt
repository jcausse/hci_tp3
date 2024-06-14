package com.grupo9.easyiot.model.routines

import kotlinx.serialization.SerialName


data class Actions (

  @SerialName("device"     ) var device     : Device,
  @SerialName("actionName" ) var actionName : String,
  @SerialName("params"     ) var params     : ArrayList<String> = arrayListOf()
  //@SerialName("meta"       ) var meta       : Meta?             = Meta()

)