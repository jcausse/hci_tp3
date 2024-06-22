package com.grupo9.easyiot

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.grupo9.easyiot.receivers.SkipNotificationReceiver
import com.grupo9.easyiot.ui.theme.EasyIoTTheme

class MainActivity : ComponentActivity() {
    private lateinit var receiver: SkipNotificationReceiver

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyIoTTheme {
                Scaffold (
                    topBar = { TopBarApp() }
                ){
                    innerPadding -> AppNavigationBar(Modifier.padding(innerPadding))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val permissionState =
                            rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
                        if (!permissionState.status.isGranted) {
                            Column {
                                val textToShow = if (permissionState.status.shouldShowRationale) {
                                    // If the user has denied the permission but the rationale can be shown,
                                    // then gently explain why the app requires this permission
                                    "Notifications are important for this app. Please grant the permission."
                                } else {
                                    // If it's the first time the user lands on this feature, or the user
                                    // doesn't want to be asked again for this permission, explain that the
                                    // permission is required
                                    "Notification permission required for this feature to be available. " +
                                            "Please grant the permission"
                                }
                                Text(textToShow)

                                LaunchedEffect(true) {
                                    permissionState.launchPermissionRequest()
                                }
                            }
                        }
                    }

                    val deviceId = intent?.getStringExtra(EasyIotIntent.DEVICE_ID)
                    if (deviceId != null) {
                        Text(
                            text = "Notification selected for device id $deviceId"
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        receiver = SkipNotificationReceiver(DEVICE_ID)
        IntentFilter(EasyIotIntent.SHOW_NOTIFICATION)
            .apply { priority = 1 }
            .also {
                var flags = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    flags = Context.RECEIVER_NOT_EXPORTED

                registerReceiver(receiver, it, flags)
            }
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(receiver)
    }

    companion object {
        // TODO: valor fijo, cambiar por un valor de dispositivo válido.
        private const val DEVICE_ID = "0511065e1e287073"
    }
}