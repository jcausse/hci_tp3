package com.grupo9.easyiot.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grupo9.easyiot.screens.DeviceDetailsScreen
import com.grupo9.easyiot.screens.DevicesScreen

@Composable
fun DevicesNavHostScreen(
    devicesState: DevicesState,
    onDeviceClick: ((String) -> Unit),
    isTablet: Boolean,
    deviceDetailsViewModel: DeviceDetailsViewModel,
    refreshDevices: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "devices"
    ) {
        composable("devices") {
            DevicesScreen(
                devicesState,
                onDeviceClick,
                isTablet,
                onDeviceDetailsClick = { id -> navController.navigate("devices/$id") },
                refreshDevices
            )
        }
        composable(
            route = "devices/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("id") ?: return@composable

            deviceDetailsViewModel.getDeviceDetails(id)
            DeviceDetailsScreen(deviceDetailsViewModel)
        }
    }
}


