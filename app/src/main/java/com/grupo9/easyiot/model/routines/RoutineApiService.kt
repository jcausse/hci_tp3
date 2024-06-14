package com.grupo9.easyiot.model.routines

import kotlinx.serialization.SerialName


data class Routines (

  @SerialName("result" ) var result : ArrayList<RoutineResult> = arrayListOf()

)