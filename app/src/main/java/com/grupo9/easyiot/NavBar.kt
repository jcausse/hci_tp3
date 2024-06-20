package com.grupo9.easyiot

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupo9.easyiot.screens.DashboardScreen
import com.grupo9.easyiot.screens.DevicesScreen
import com.grupo9.easyiot.screens.DevicesTabletScreen
import com.grupo9.easyiot.screens.DevicesViewModel
import com.grupo9.easyiot.screens.RoutinesLandscapeScreen
import com.grupo9.easyiot.screens.RoutinesScreen
import com.grupo9.easyiot.screens.RoutinesViewModel


@Composable
fun AppNavigationBar(modifier: Modifier = Modifier) {
    var currentDirection by rememberSaveable { mutableStateOf(NavIcons.DASHBOARD) }
    //ViewModels of all the screens
    val routinesViewModel: RoutinesViewModel = viewModel()
    val devicesViewModel: DevicesViewModel = viewModel()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val isTablet = if (isLandscape) {
        configuration.screenWidthDp > 840
    } else {
        configuration.screenWidthDp > 600
    }

    if(!isTablet) {
        if (isLandscape) {
            currentDirection = NavIcons.ROUTINES
            Row {
                NavigationRail(
                    modifier = modifier.fillMaxHeight(),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    NavIcons.entries.forEach {
                        NavigationRailItem(
                            selected = currentDirection == it,
                            onClick = { currentDirection = it },
                            modifier = Modifier.padding(vertical = 16.dp),
                            icon = {
                                Icon(
                                    painter = painterResource(id = it.icon),
                                    contentDescription = stringResource(id = it.contentDescriptor),
                                    tint = if (currentDirection == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer
                                )
                            },
                            label = {
                                Text(
                                    stringResource(id = it.label),
                                    color = if (currentDirection == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer
                                )
                            }
                        )
                    }
                }
                when (currentDirection) {
                    NavIcons.DEVICES -> DevicesScreen(
                        devicesViewModel.devicesState,
                        devicesViewModel::addRecent,
                        isTablet
                    )

                    NavIcons.DASHBOARD -> DashboardScreen(
                        devicesViewModel.recentDevices,
                        devicesViewModel.devicesState
                    )

                    NavIcons.ROUTINES -> RoutinesLandscapeScreen(
                        routinesViewModel.routinesState,
                        modifier
                    )
                }
            }

        } else {
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
                            label = {
                                Text(
                                    stringResource(id = it.label),
                                    color = if (currentDirection == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer
                                )
                            },
                            selected = currentDirection == it,
                            onClick = { currentDirection = it }
                        )
                    }
                },
                modifier = modifier
            ) {
                when (currentDirection) {
                    NavIcons.DEVICES -> DevicesScreen(
                        devicesViewModel.devicesState,
                        devicesViewModel::addRecent,
                        isTablet
                    )

                    NavIcons.DASHBOARD -> DashboardScreen(
                        devicesViewModel.recentDevices,
                        devicesViewModel.devicesState
                    )

                    NavIcons.ROUTINES -> RoutinesScreen(routinesViewModel.routinesState)
                }
            }
        }
    } else {
        if (isLandscape) {
            currentDirection = NavIcons.DEVICES
            Row {
                NavigationRail(
                    modifier = modifier.fillMaxHeight(),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    NavIcons.entries.forEach {
                        NavigationRailItem(
                            selected = currentDirection == it,
                            onClick = { currentDirection = it },
                            modifier = Modifier.padding(vertical = 16.dp),
                            icon = {
                                Icon(
                                    painter = painterResource(id = it.icon),
                                    contentDescription = stringResource(id = it.contentDescriptor),
                                    tint = if (currentDirection == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer
                                )
                            },
                            label = {
                                Text(
                                    stringResource(id = it.label),
                                    color = if (currentDirection == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer
                                )
                            }
                        )
                    }
                }
                when (currentDirection) {
                    NavIcons.DEVICES -> DevicesTabletScreen(
                        devicesViewModel.devicesState,
                        isTablet
                    )

                    NavIcons.DASHBOARD -> DashboardScreen(
                        devicesViewModel.recentDevices,
                        devicesViewModel.devicesState
                    )

                    NavIcons.ROUTINES -> RoutinesLandscapeScreen(
                        routinesViewModel.routinesState,
                        modifier
                    )
                }
            }

        }
    }
}

@Composable
fun LeftNavBar(modifier: Modifier) {
    var selectedItem by remember { mutableStateOf(NavIcons.DASHBOARD) }

    NavigationRail(
        modifier = modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        NavIcons.entries.forEach {
            NavigationRailItem(
                selected = selectedItem == it,
                onClick = { selectedItem = it },
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = stringResource(id = it.contentDescriptor)
                    )
                },
                label = { Text(stringResource(id = it.label),
                    color = if (selectedItem == it) Color.Yellow else MaterialTheme.colorScheme.primaryContainer
                )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    AppNavigationBar()
}


enum class NavIcons (
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @StringRes val contentDescriptor: Int
){
    DEVICES(R.string.bottom_navigation_devices, R.drawable.devices, R.string.bottom_navigation_devices),
    DASHBOARD(R.string.bottom_navigation_dashboard, R.drawable.dash, R.string.bottom_navigation_dashboard),
    ROUTINES(R.string.bottom_navigation_routines, R.drawable.routine, R.string.bottom_navigation_routines)
}
