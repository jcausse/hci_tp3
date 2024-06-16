package com.grupo9.easyiot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.grupo9.easyiot.ui.theme.EasyIoTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            EasyIoTTheme {
                EasyIoTApp()
            }
        }
    }
}