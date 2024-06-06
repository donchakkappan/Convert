package com.allutils.app_style_guide.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.allutils.app_style_guide.theme.ConvertTheme

@Composable
fun SolidButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun OutlineButton(text: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        Text(text = text)
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit) {
    androidx.compose.material3.TextButton(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        Text(text = text)
    }
}

@Composable
fun IconButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = text)
        }
    }
}

@Composable
fun RoundedButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        Text(text = text)
    }
}

@Composable
fun DisabledButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(8.dp), enabled = false) {
        Text(text = text)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
@Composable
fun Preview_1() {
    ConvertTheme {
        Column {
            Row {
                SolidButton("Solid Button") {}
                OutlineButton("Outlined") {}
                TextButton("Text Button") {}
            }
            Row {
                IconButton("Icon", Icons.Default.FavoriteBorder) {}
                RoundedButton("Rounded") {}
                DisabledButton("Disabled") {}
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
@Composable
fun Preview_2() {
    ConvertTheme {
        Column {
            Row {
                SolidButton("Solid Button") {}
                OutlineButton("Outlined") {}
                TextButton("Text Button") {}
            }
            Row {
                IconButton("Icon", Icons.Default.FavoriteBorder) {}
                RoundedButton("Rounded") {}
                DisabledButton("Disabled") {}
            }
        }
    }
}