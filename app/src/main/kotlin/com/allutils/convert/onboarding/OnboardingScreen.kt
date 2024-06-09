package com.allutils.convert.onboarding

import android.content.Intent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.components.PrimaryButton
import com.allutils.app_style_guide.components.TertiaryButton
import com.allutils.app_style_guide.components.carousel.Pager
import com.allutils.app_style_guide.components.carousel.PagerState
import com.allutils.convert.NavHostActivity

@Composable
fun OnBoardingScreen() {
    val context = LocalContext.current

    val pagerState: PagerState = run {
        remember {
            PagerState(0, 0, onboardingList.size - 1)
        }
    }
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Pager(
                state = pagerState,
                orientation = Orientation.Horizontal,
                modifier = Modifier.fillMaxSize()
            ) {
                OnboardingPagerItem(onboardingList[commingPage])
            }

            TertiaryButton(
                "Skip", Modifier
                    .align(Alignment.TopEnd)
                    .padding(vertical = 48.dp, horizontal = 16.dp)
            ) {
                context.startActivity(Intent(context, NavHostActivity::class.java))
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp)
            ) {
                onboardingList.forEachIndexed { index, _ ->
                    OnboardingPagerSlide(
                        selected = index == pagerState.currentPage,
                        MaterialTheme.colorScheme.primary,
                        Icons.Filled.Star
                    )
                }
            }

            val buttonText =
                if (pagerState.currentPage == onboardingList.size - 1) "Let's Begin" else "Next"

            PrimaryButton(
                buttonText,
                Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp)
            ) {
                if (pagerState.currentPage == onboardingList.size - 1)
                    context.startActivity(Intent(context, NavHostActivity::class.java))

                if (pagerState.currentPage != onboardingList.size - 1) pagerState.currentPage += 1
            }
        }
    }
}

data class Onboard(val title: String, val description: String, val lottieFile: String)

val onboardingList = listOf(
    Onboard(
        "Personalize Your Experience",
        "Choose the utilities you need: EMI calculator, currency converter, and more",
        "welcome.json"
    ),
    Onboard(
        "Select Your Tools",
        "Pick your preferred services to customize your app: EMI, currency, units",
        "setup.json"
    ),
    Onboard(
        "Customize Your Dashboard",
        "Select services like EMI, currency, and unit converters to tailor your app.",
        "done.json"
    )
)

