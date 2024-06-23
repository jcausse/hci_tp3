package com.grupo9.easyiot

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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
                        val permissionState = rememberPermissionState(
                            permission = android.Manifest.permission.POST_NOTIFICATIONS
                        )
                        if (!permissionState.status.isGranted){
                            LaunchedEffect(true) {
                                permissionState.launchPermissionRequest()
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
        private const val DEVICE_ID = "3caf1f787ab20a75"
    }
}