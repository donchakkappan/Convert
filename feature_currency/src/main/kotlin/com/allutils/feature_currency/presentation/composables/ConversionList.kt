package com.allutils.feature_currency.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.CurrencyListViewModel

@Composable
internal fun HomeScreen(viewModel: CurrencyListViewModel) {
    val uiState: CurrencyListViewModel.UiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            CurrencyListViewModel.UiState.Error -> DataNotFoundAnim()
            CurrencyListViewModel.UiState.Loading -> ProgressIndicator()
            is CurrencyListViewModel.UiState.Content -> ConversionListScreen(it.conversionRates)
        }
    }
}

@Composable
internal fun ConversionListScreen(
    conversionRates: List<ConversionRatesOutput>,
    viewModel: CurrencyListViewModel? = null
) {
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items(items = conversionRates, key = { it.currencyCode }) { currency ->
            CurrencyListItem(
                "USD",
                currency.currencyCode,
                currency.rate.toString(),
                "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png"
            )
        }
    }
}