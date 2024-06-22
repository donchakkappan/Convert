package com.allutils.convert

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.nav.NavManager
import com.allutils.base.presentation.activity.BaseActivity
import com.allutils.base.presentation.ext.navigateSafe
import com.allutils.convert.databinding.ActivityNavHostBinding
import com.allutils.convert.presentation.AppViewModel
import com.allutils.convert.presentation.onboarding.OnBoardingScreen
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NavHostActivity : BaseActivity(R.layout.activity_nav_host) {

    private val binding: ActivityNavHostBinding by viewBinding()
    private val navManager: NavManager by inject()
    private val viewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyOnboardingStatus()
    }

    private fun verifyOnboardingStatus() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (viewModel.isUserOnboarded()) {
                    initNavManager()
                    initBottomNavigation()
                } else {
                    viewModel.onUserOnboarded()
                    setContent {
                        ConvertTheme {
                            OnBoardingScreen()
                        }
                    }
                }
            }
        }
    }

    private fun initBottomNavigation() {
        val navController = binding.navHostFragment.getFragment<DynamicNavHostFragment>().navController
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun initNavManager() {
        navManager.setOnNavEvent {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            currentFragment?.navigateSafe(it)
        }
    }

}
