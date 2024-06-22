package com.grupo9.easyiot.screens

import com.grupo9.easyiot.model.device.Devices

sealed interface DevicesState{
    //success
    data class Success(val get: Devices) : DevicesState
    //error
    data class Error(val message: String) : DevicesState
    // loading
    data object Loading : DevicesState
}
