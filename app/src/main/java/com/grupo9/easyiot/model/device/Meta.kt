package com.grupo9.easyiot.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("house" ) var house : String = "",
    @SerialName("room" ) var room : String = "",
    @SerialName("fav" ) var fav : Boolean
)