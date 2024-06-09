package com.allutils.feature_currency.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.app_style_guide.components.NumberField
import com.allutils.app_style_guide.styles.darkestBlue
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.R
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.CurrencyListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTopAppBar(viewModel: CurrencyListViewModel?) {

    TopAppBar(
        title = {
            NumberField("Enter amount ")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = lightestGrey,
                    contentDescription = stringResource(id = R.string.conversion_rates)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                viewModel?.amount = 4
                viewModel?.getConversionRates("USD")
            }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    tint = lightestGrey,
                    contentDescription = stringResource(id = R.string.country_flag)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = darkestBlue,
            titleContentColor = lightestGrey
        )
    )
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConversionListScreen(
    conversionRates: List<ConversionRatesOutput>,
    viewModel: CurrencyListViewModel? = null
) {

    Scaffold(
        topBar = {
            CustomTopAppBar(viewModel)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply the inner padding to avoid overlap with the top app bar
        ) {
            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                items(items = conversionRates, key = { it.currencyCode }) { currency ->
                    CurrencyListItem(
                        "USD",
                        currency.currencyCode,
                        currency.rate.toString(),
                        "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png",
                        viewModel?.amount ?:0
                    )
                }
            }
        }


    }


}