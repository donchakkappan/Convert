package com.allutils.app_style_guide.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFFADD8E6), // Light Blue
    onPrimary = Color.Black,
    secondary = Color.White,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    background = Color(0xFFF5F5F5) // Light Gray
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF006400), // Dark Green
    onPrimary = Color.White,
    secondary = Color(0xFF32CD32), // Light Green
    onSecondary = Color.Black,
    surface = Color(0xFF333333), // Dark Gray
    onSurface = Color.White,
    background = Color(0xFF121212) // Very Dark Gray
)

@Composable
fun ConvertTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = appTypography,
        shapes = Shapes,
        content = content
    )
}