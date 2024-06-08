package com.allutils.app_style_guide.components.carousel

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CarouselDot(selected: Boolean, color: Color, icon: ImageVector) {
    Icon(
        imageVector = icon,
        modifier = Modifier
            .padding(4.dp)
            .size(12.dp),
        contentDescription = null,
        tint = if (selected) color else Color.Gray
    )
}

@Preview
@Composable
fun PreviewPaginationPionters() {
    CarouselDot(
        true,
        MaterialTheme.colorScheme.onPrimary,
        Icons.Filled.Favorite
    )
    CarouselDot(
        true,
        MaterialTheme.colorScheme.onPrimary,
        Icons.Filled.Favorite
    )
    CarouselDot(
        true,
        MaterialTheme.colorScheme.onPrimary,
        Icons.Filled.Favorite
    )
}
