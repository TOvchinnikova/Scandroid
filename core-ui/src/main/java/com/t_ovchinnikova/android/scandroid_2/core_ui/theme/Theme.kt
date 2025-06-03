package com.t_ovchinnikova.android.scandroid_2.core_ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DarkColorPalette = darkColorScheme(
    primary = ColorPrimary,
    secondary = ColorSecondary,
    background = DarkColorBackground,
    surface = DarkColorBackground,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceContainer = DarkColorNavigationBackground,
)

private val LightColorPalette = lightColorScheme(
    primary = ColorPrimary,
    secondary = ColorSecondary,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    surfaceContainer = ColorNavigationBackground,
)

@Composable
fun ScandroidTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val view = LocalView.current
    val navigationBarColor = remember { getNavigationBottomBackgroundColor(isDarkTheme) }
    SideEffect {
        val window = (view.context as? Activity)?.window
        if (window != null) {
            window.navigationBarColor = navigationBarColor.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}