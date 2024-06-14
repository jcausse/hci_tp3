package com.grupo9.easyiot.model.device

import com.grupo9.easyiot.model.routines.RoutineResult
import kotlinx.serialization.SerialName


data class Devices (

  @SerialName("result" ) var result : ArrayList<DeviceResult> = arrayListOf()

)