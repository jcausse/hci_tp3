package com.grupo9.easyiot

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EasyIoTApp(){
    Scaffold (
        topBar = { TopBarApp() }
    ) {
            innerPadding -> AppNavigationBar(Modifier.padding(innerPadding))
    }
}