package com.grupo9.easyiot.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.easyiot.model.device.Devices
import com.grupo9.easyiot.network.DeviceApi
import kotlinx.coroutines.launch

class DevicesViewModel : ViewModel() {
    var devicesState: DevicesState by mutableStateOf(DevicesState.Loading)
        private set

    init {
        getDevices()
    }
    fun getDevices(){
        viewModelScope.launch {
            devicesState = DevicesState.Loading
            devicesState = try {
                val result = DeviceApi.retorfitService.getDeviceList()
                print(result)
                DevicesState.Success(result)
            }catch (e: Exception){
                DevicesState.Error
            }
        }

    }
}


sealed interface DevicesState{
    //success
    data class Success(val get: Devices) : DevicesState
    //error
    data object Error : DevicesState
    // loading
    data object Loading : DevicesState

}

fun main(){
    val devs = DevicesViewModel()
    devs.getDevices()
}