package com.grupo9.easyiot.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.easyiot.model.routines.Routines
import com.grupo9.easyiot.network.RoutineApi
import kotlinx.coroutines.launch

class RoutinesViewModel : ViewModel() {
    var routinesState: RoutinesState by mutableStateOf(RoutinesState.Loading)
    private set
    var executeResult: Boolean by mutableStateOf(true)

    init {
       getRoutines()
    }
    fun getRoutines(){
        viewModelScope.launch {
            routinesState = RoutinesState.Loading
            routinesState = try {
                val result = RoutineApi.retorfitService.getRoutineList()
                print(result)
                RoutinesState.Success(result)
            }catch (e: Exception){
                RoutinesState.Error("Unexpected error: ${e.message}")
            }
        }

    }

    fun executeRoutine(routineId: String){
        viewModelScope.launch {
            try {
                executeResult = RoutineApi.retorfitService.executeRoutine(routineId)
            }catch (e: Exception){
                RoutinesState.Error("Unexpected error: ${e.message}")
            }
        }
    }
}


sealed interface RoutinesState{
    //success
    data class Success(val get: Routines) : RoutinesState
    //error
    data class Error(val message: String) : RoutinesState
    // loading
    data object Loading : RoutinesState

}