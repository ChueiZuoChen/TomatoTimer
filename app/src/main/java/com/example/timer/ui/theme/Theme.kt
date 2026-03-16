package com.example.timer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = TomatoPrimaryDark,
    onPrimary = TomatoOnPrimaryDark,
    primaryContainer = TomatoPrimaryContainerDark,
    onPrimaryContainer = TomatoOnPrimaryContainerDark,
    secondary = TomatoSecondaryDark,
    onSecondary = TomatoOnSecondaryDark,
    secondaryContainer = TomatoSecondaryContainerDark,
    onSecondaryContainer = TomatoOnSecondaryContainerDark,
    tertiary = TomatoTertiaryDark,
    onTertiary = TomatoOnTertiaryDark,
    tertiaryContainer = TomatoTertiaryContainerDark,
    onTertiaryContainer = TomatoOnTertiaryContainerDark
)

private val LightColorScheme = lightColorScheme(
    primary = TomatoPrimary,
    onPrimary = TomatoOnPrimary,
    primaryContainer = TomatoPrimaryContainer,
    onPrimaryContainer = TomatoOnPrimaryContainer,
    secondary = TomatoSecondary,
    onSecondary = TomatoOnSecondary,
    secondaryContainer = TomatoSecondaryContainer,
    onSecondaryContainer = TomatoOnSecondaryContainer,
    tertiary = TomatoTertiary,
    onTertiary = TomatoOnTertiary,
    tertiaryContainer = TomatoTertiaryContainer,
    onTertiaryContainer = TomatoOnTertiaryContainer
)

@Composable
fun TimerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
