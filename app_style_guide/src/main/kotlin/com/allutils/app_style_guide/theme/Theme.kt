package com.allutils.app_style_guide.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Deep Purple 500
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBB86FC), // Light Purple
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF03DAC6), // Teal 200
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF018786), // Teal 700
    onSecondaryContainer = Color.White,
    background = Color(0xFFFFFFFF), // White
    onBackground = Color.Black,
    surface = Color(0xFFFFFFFF), // White
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFEEEEEE), // Light Gray
    onSurfaceVariant = Color.Black,
    error = Color(0xFFB00020), // Red 700
    onError = Color.White,
    errorContainer = Color(0xFFCF6679), // Light Red
    onErrorContainer = Color.Black
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC), // Light Purple
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF3700B3), // Deep Purple 700
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF03DAC6), // Teal 200
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF03DAC5), // Slightly darker Teal
    onSecondaryContainer = Color.Black,
    background = Color(0xFF121212), // Dark Gray
    onBackground = Color.White,
    surface = Color(0xFF121212), // Dark Gray
    onSurface = Color.White,
    surfaceVariant = Color(0xFF37474F), // Darker Gray
    onSurfaceVariant = Color.White,
    error = Color(0xFFCF6679), // Light Red
    onError = Color.Black,
    errorContainer = Color(0xFFB00020), // Red 700
    onErrorContainer = Color.White
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
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}