package com.grupo9.easyiot.model.routines

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("description" ) var description : String = "",
    @SerialName("weekdays" ) var weekdays : String = ""
)