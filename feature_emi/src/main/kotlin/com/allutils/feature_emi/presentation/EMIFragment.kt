package com.allutils.feature_emi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.presentation.activity.BaseFragment
import com.allutils.feature_emi.emiModules
import com.allutils.feature_emi.presentation.home.EMICalculatorHome
import com.allutils.feature_emi.presentation.home.EMICalculatorViewModel
import com.allutils.feature_emi.presentation.home.model.EmiIntents
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class EMIFragment : BaseFragment() {

    private val emiCalculatorViewModel: EMICalculatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(emiModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ConvertTheme {
                    EMICalculatorHome(emiCalculatorViewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emiCalculatorViewModel.processIntent(EmiIntents.LoadInitialValues)
    }

}
