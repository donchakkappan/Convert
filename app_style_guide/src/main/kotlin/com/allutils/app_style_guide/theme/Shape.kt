package com.allutils.app_style_guide.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(topEnd = 25.dp),
    medium = RoundedCornerShape(4.dp),
    large = CutCornerShape(topStart = 50.dp)
)