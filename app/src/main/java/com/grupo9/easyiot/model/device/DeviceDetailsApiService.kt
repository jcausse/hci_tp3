package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDetails (
    @SerialName("result") var result : DeviceResult
)

@Serializable
data class ExecuteActionResult (
    @SerialName("result") var result: Boolean
)