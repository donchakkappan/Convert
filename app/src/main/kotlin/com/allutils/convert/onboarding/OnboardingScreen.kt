package com.allutils.convert.onboarding

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.components.carousel.Pager
import com.allutils.app_style_guide.components.carousel.PagerState
import com.allutils.convert.NavHostActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(onSkip: () -> Unit) {
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
            Text(
                text = "Skip",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(vertical = 48.dp, horizontal = 16.dp)
                    .clickable(onClick = onSkip)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp)
            ) {
                onboardingList.forEachIndexed { index, _ ->
                    OnboardingPagerSlide(
                        selected = index == pagerState.currentPage,
                        MaterialTheme.colorScheme.primary,
                        Icons.Filled.Favorite
                    )
                }
            }
            val context = LocalContext.current
            Button(

                onClick = {
                    if (pagerState.currentPage == onboardingList.size - 1)
                        context.startActivity(Intent(context, NavHostActivity::class.java))

                    if (pagerState.currentPage != onboardingList.size - 1) pagerState.currentPage =
                        pagerState.currentPage + 1
                },
                modifier = Modifier
                    .animateContentSize()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            ) {
                Text(
                    text = if (pagerState.currentPage == onboardingList.size - 1) "Let's Begin" else "Next",
                    modifier = Modifier.padding(horizontal = 32.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

data class Onboard(val title: String, val description: String, val lottieFile: String)

val onboardingList = listOf(
    Onboard(
        "Personalize Your Experience",
        "Choose the utilities you need: EMI calculator, currency converter, and more",
        "profile.json"
    ),
    Onboard(
        "Select Your Tools",
        "Pick your preferred services to customize your app: EMI, currency, units",
        "working.json"
    ),
    Onboard(
        "Customize Your Dashboard",
        "Select services like EMI, currency, and unit converters to tailor your app.",
        "food.json"
    )
)

