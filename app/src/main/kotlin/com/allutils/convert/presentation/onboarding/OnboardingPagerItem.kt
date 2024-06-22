package com.allutils.convert.presentation.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.animations.LottieLoadingView
import com.allutils.app_style_guide.styles.bodyS
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.mediumBlack

@Composable
fun OnboardingPagerItem(item: Onboard) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieLoadingView(
            context = LocalContext.current,
            file = item.lottieFile,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Text(
            text = item.title,
            style = headingH4,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = item.description,
            style = bodyS,
            color = mediumBlack,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
