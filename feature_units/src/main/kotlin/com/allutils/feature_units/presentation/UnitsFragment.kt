package com.allutils.feature_units.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.allutils.app_style_guide.templates.ComingSoonScreen
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.presentation.activity.BaseFragment
import com.allutils.feature_units.unitsModules
import org.koin.core.context.loadKoinModules

class UnitsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(unitsModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ConvertTheme {
                    ComingSoonScreen()
                }
            }
        }
    }

}
