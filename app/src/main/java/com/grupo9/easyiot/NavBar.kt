package com.grupo9.easyiot



import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
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
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigationBar(modifier: Modifier = Modifier) {
    var currentDirection by rememberSaveable { mutableStateOf(NavIcons.DASHBOARD) }
    NavigationSuiteScaffold(
        navigationSuiteColors = NavigationSuiteDefaults.colors(),
        navigationSuiteItems = {
            NavIcons.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painter = painterResource(id = it.icon),
                            contentDescription = stringResource(id = it.contentDescriptor)
                        )
                    },
                    label = { Text(stringResource(id = it.label))},
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
                NavIcons.ROUTINES -> RoutinesScreen()
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
@Composable
fun RoutinesScreen() {
    Text(text = "Routines")
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
