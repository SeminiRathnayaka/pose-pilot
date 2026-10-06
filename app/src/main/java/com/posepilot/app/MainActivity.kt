package com.posepilot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.posepilot.app.core.navigation.PosePilotApp
import com.posepilot.app.core.theme.PosePilotTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PosePilotTheme {
                PosePilotApp()
            }
        }
    }
}
