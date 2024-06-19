package com.grupo9.easyiot.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.grupo9.easyiot.model.device.Devices

class DashboardViewModel : ViewModel(){
    var dashboardState: DashboardState by mutableStateOf(DashboardState.Loading)
    private set
}

sealed interface DashboardState{
    //success
    data class Success(val get: Devices) : DashboardState
    //error
    data class Error(val message: String) : DashboardState
    //loading
    data object Loading : DashboardState
}