package com.example.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable Edge-to-Edge display
        enableEdgeToEdge()
        
        setContent {
            // Disable dynamicColor to ensure the "tomato" red theme is applied
            TimerTheme(dynamicColor = false) {
                TimerScreen()
            }
        }
    }
}
