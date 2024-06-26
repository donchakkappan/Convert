package com.allutils.feature_currency.presentation.basecode

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.AvailableCountriesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BasecodeList(sheetState: ModalBottomSheetState, countriesViewModel: AvailableCountriesViewModel) {
    val uiState: AvailableCountriesViewModel.UiState by countriesViewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            AvailableCountriesViewModel.UiState.Error -> DataNotFoundAnim()
            AvailableCountriesViewModel.UiState.Loading -> ProgressIndicator()
            is AvailableCountriesViewModel.UiState.Content -> BasecodeList(
                sheetState,
                it.conversionRates,
                countriesViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun BasecodeList(
    sheetState: ModalBottomSheetState,
    conversionRates: List<ConversionRatesOutput>,
    countriesViewModel: AvailableCountriesViewModel? = null
) {
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items(items = conversionRates, key = { it.currencyCode }) { currency ->
            BasecodeListItem(
                currency.currencyCode,
                "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png"
            ) {
                countriesViewModel?.baseCode = it
                countriesViewModel?.getConversionRates()
                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        }
    }

}
