package com.allutils.app_style_guide.templates

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.allutils.app_style_guide.R

@Composable
fun PlaceholderImage(
    url: Any?,
    color: Color,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Surface(color = color, modifier = modifier) {
        val randomPlaceHolder by rememberSaveable {
            mutableStateOf(R.drawable.ic_image_placeholder)
        }

        val model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true).build()

        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            placeholder = painterResource(randomPlaceHolder),
        )
    }
}
