package com.allutils.app_style_guide.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.app_style_guide.theme.appTypography

@Composable
fun SmallText(text: String) {
    Text(
        text = text,
        style = appTypography.labelSmall,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun MediumText(text: String) {
    Text(
        text = text,
        style = appTypography.labelMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun LargeText(text: String) {
    Text(
        text = text,
        style = appTypography.labelLarge,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
@Composable
private fun Preview_1() {
    ConvertTheme {
        Column {
            Row {
                SmallText("Small Text")
                MediumText("Medium Text")
                LargeText("Large Text")
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
@Composable
private fun Preview_2() {
    ConvertTheme {
        Column {
            Row {
                SmallText("Small Text")
                MediumText("Medium Text")
                LargeText("Large Text")
            }
        }
    }
}