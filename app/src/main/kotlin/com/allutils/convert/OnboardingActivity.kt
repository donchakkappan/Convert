package com.allutils.convert

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.convert.onboarding.OnBoardingScreen

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConvertTheme {
                OnBoardingScreen {}
            }
        }

    }
}