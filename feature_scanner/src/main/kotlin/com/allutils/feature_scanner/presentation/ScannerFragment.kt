package com.allutils.feature_scanner.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.allutils.app_style_guide.templates.ComingSoonScreen
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.presentation.activity.BaseFragment
import com.allutils.feature_scanner.scannerModules
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ScannerFragment : BaseFragment() {

    private val scannerViewModel: ScannerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(scannerModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ConvertTheme {
                    ScannerHome(scannerViewModel)
                }
            }
        }
    }

}
