package com.grupo9.easyiot



import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupo9.easyiot.screens.RoutinesScreen
import com.grupo9.easyiot.screens.RoutinesViewModel


@Composable
fun AppNavigationBar(modifier: Modifier = Modifier) {
    var currentDirection by rememberSaveable { mutableStateOf(NavIcons.DASHBOARD) }
    //ViewModels of all the screens
    val routinesViewModel: RoutinesViewModel = viewModel()

    NavigationSuiteScaffold(
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = MaterialTheme.colorScheme.primary
        ),
        navigationSuiteItems = {
            NavIcons.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painter = painterResource(id = it.icon),
                            contentDescription = stringResource(id = it.contentDescriptor),
                            tint = if (currentDirection == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer

                        )
                    },
                    label = { Text(
                        stringResource(id = it.label),
                        color = if (currentDirection == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer
                    )},
                    selected = currentDirection == it,
                    onClick = { currentDirection = it }
                )
            }

        },
        modifier = modifier
    ){
        Column {
            when (currentDirection) {
                NavIcons.DEVICES -> DevicesScreen()
                NavIcons.DASHBOARD -> DashboardScreen()
                NavIcons.ROUTINES -> RoutinesScreen(routinesViewModel.routinesState)
            }
        }

    }
}
@Composable
fun DevicesScreen() {
    Column {
        for (i in 1..10) {
            Text(text = "Devices")
        }
    }


}

@Composable
fun DashboardScreen() {
    Text(text = "DashBoard")
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    AppNavigationBar()
}


enum class NavIcons (
    @StringRes val label: Int,
    val icon: Int,
    @StringRes val contentDescriptor: Int
){
    DEVICES(R.string.bottom_navigation_devices, R.drawable.devices, R.string.bottom_navigation_devices),
    DASHBOARD(R.string.bottom_navigation_dashboard, R.drawable.dash, R.string.bottom_navigation_dashboard),
    ROUTINES(R.string.bottom_navigation_routines, R.drawable.routine, R.string.bottom_navigation_routines)
}

/*
@Composable
fun NavigationScaffold() {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    if (adaptiveInfo.windowPosture == WindowPosture.LANDSCAPE) {
        LandscapeContent()
    } else {
        PortraitContent()
    }
}

@Composable
fun PortraitContent() {

}

@Composable
fun LandscapeContent() {

}*/
