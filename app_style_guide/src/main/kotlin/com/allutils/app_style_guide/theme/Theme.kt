package com.allutils.app_style_guide.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.allutils.app_style_guide.styles.darkGrey
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.darkestBlue
import com.allutils.app_style_guide.styles.darkestGrey
import com.allutils.app_style_guide.styles.lightGrey
import com.allutils.app_style_guide.styles.lightestGrey

val LightColorScheme = lightColorScheme(
    primary = darkestBlue,
    onPrimary = lightestGrey,
    secondary = Color.White,
    onSecondary = Color.Black,
    surface = Color.White,
    outline = darkestBlue,
    onSurface = Color.Black,
    background = lightGrey
)

val DarkColorScheme = darkColorScheme(
    primary = darkestBlack,
    onPrimary = Color.White,
    secondary = lightGrey,
    onSecondary = Color.Black,
    outline = darkestBlack,
    surface = darkGrey,
    onSurface = lightestGrey,
    background = darkestGrey
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