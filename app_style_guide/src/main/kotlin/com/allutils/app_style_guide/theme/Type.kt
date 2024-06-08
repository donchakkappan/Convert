package com.allutils.app_style_guide.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.allutils.app_style_guide.R
import com.allutils.app_style_guide.styles.actionM
import com.allutils.app_style_guide.styles.actionS
import com.allutils.app_style_guide.styles.bodyL
import com.allutils.app_style_guide.styles.bodyM
import com.allutils.app_style_guide.styles.bodyS
import com.allutils.app_style_guide.styles.captionM
import com.allutils.app_style_guide.styles.headingH4

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_thin, FontWeight.Thin)
)

val OpenSansFontFamily = FontFamily(
    Font(R.font.opensans_bold, FontWeight.Bold),
    Font(R.font.opensans_light, FontWeight.Light),
    Font(R.font.opensans_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal)
)

val appTypography = Typography(
    bodyLarge = bodyL,
    bodyMedium = bodyM,
    bodySmall = bodyS,
    displayMedium = headingH4,
    labelSmall = actionS,
    labelLarge = captionM,
    labelMedium = actionM,
)