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
    deviceDetailsViewModel: DeviceDetailsViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "devices"
    ) {
        composable("devices") {
            DevicesScreen(
                devicesState,
                onDeviceClick = { id -> navController.navigate("devices/$id") }
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


