package com.grupo9.easyiot.model.routines

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Routines (
  @SerialName("result" ) var result : ArrayList<RoutineResult> = arrayListOf()

)