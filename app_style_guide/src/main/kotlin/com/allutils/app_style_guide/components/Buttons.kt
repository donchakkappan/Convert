package com.allutils.app_style_guide.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.styles.actionM
import com.allutils.app_style_guide.theme.ConvertTheme

@Composable
fun PrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp).defaultMinSize(150.dp, 50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, style = actionM)
    }
}

@Composable
fun SecondaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, style = actionM)
    }
}

@Composable
fun TertiaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    androidx.compose.material3.TextButton(onClick = onClick, modifier = modifier.padding(8.dp)) {
        Text(text = text, style = actionM)
    }
}

@Composable
fun IconButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = text, style = actionM)
        }
    }
}

@Composable
fun DisabledButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = false
    ) {
        Text(text = text, style = actionM)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
@Composable
private fun Preview_1() {
    ConvertTheme {
        Column {
            Row {
                PrimaryButton("Solid Button") {}
                SecondaryButton("Outlined") {}
                TertiaryButton("Text Button") {}
            }
            Row {
                IconButton("Icon", Icons.Default.FavoriteBorder) {}
                DisabledButton("Disabled") {}
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
                PrimaryButton("Solid Button") {}
                SecondaryButton("Outlined") {}
                TertiaryButton("Text Button") {}
            }
            Row {
                IconButton("Icon", Icons.Default.FavoriteBorder) {}
                DisabledButton("Disabled") {}
            }
        }
    }
}