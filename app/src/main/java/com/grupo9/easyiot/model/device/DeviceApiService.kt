package com.grupo9.easyiot.model.device

import com.grupo9.easyiot.model.routines.RoutineResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class Devices (

  @SerialName("result" ) var result : List<DeviceResult> = emptyList()

)