package com.allutils.feature_currency.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.presentation.activity.BaseFragment
import com.allutils.feature_currency.currencyModules
import com.allutils.feature_currency.presentation.basecode.BasecodeViewModel
import com.allutils.feature_currency.presentation.countries.CountriesViewModel
import com.allutils.feature_currency.presentation.home.ConversionListViewModel
import com.allutils.feature_currency.presentation.home.CurrencyConversionHome
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ConversionListFragment : BaseFragment() {

    private val conversionsViewModel: ConversionListViewModel by viewModel()
    private val countriesViewModel: CountriesViewModel by viewModel()
    private val basecodeViewModel: BasecodeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(currencyModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ConvertTheme {
                    CurrencyConversionHome(
                        conversionsViewModel,
                        basecodeViewModel,
                        countriesViewModel
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conversionsViewModel.handleAction(ConversionListViewModel.MainAction.LoadPreference)
        conversionsViewModel.showConversionRates()
        countriesViewModel.getConversionRates()
        basecodeViewModel.loadBasecodeList()
    }
}
