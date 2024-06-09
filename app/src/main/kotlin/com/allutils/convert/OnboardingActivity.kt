package com.allutils.convert

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.convert.onboarding.OnBoardingScreen

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConvertTheme {
                OnBoardingScreen()
            }
        }

    }
}