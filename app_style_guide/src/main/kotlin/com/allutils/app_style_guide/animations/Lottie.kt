package com.allutils.app_style_guide.animations

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.allutils.app_style_guide.components.TextTitleMedium

@Composable
fun LabeledAnimation(@StringRes label: Int, @RawRes assetResId: Int) {
    Card(
        modifier = Modifier
            .wrapContentSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .padding(64.dp),
        ) {
            TextTitleMedium(text = stringResource(label))
            LottieAssetLoader(assetResId)
        }
    }
}

@Composable
fun LottieAssetLoader(@RawRes assetResId: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(assetResId))

    LottieAnimation(
        composition,
        modifier = Modifier.requiredSize(200.dp),
    )
}
