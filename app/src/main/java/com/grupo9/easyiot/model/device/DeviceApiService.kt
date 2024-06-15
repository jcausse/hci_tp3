package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Devices (

  @SerialName("result" ) var result : ArrayList<DeviceResult> = arrayListOf()

)