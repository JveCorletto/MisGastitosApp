package com.misgastitos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.misgastitos.app.ui.nav.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import com.misgastitos.app.ui.theme.MisGastitosAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MisGastitosAppTheme {
                AppNavHost() // ← Esto debe llamar a tu AppNavHost completo
            }
        }
    }
}