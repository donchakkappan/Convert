package com.allutils.feature_currency.presentation.conversion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.app_style_guide.components.NumberField
import com.allutils.app_style_guide.styles.darkestBlue
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.ConversionListViewModel
import com.allutils.feature_currency.presentation.basecode.BasecodeList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ConversionAppBar(
    sheetState: ModalBottomSheetState,
    viewModel: ConversionListViewModel?
) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = {
            NumberField("Enter amount ") {
                viewModel?.amount = it.toDouble()
                viewModel?.getConversionRates()
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    sheetState.show()
                }
            }) {
                androidx.compose.material3.Text(
                    text = viewModel?.baseCode.toString(),
                    style = headingH4,
                    color = lightestGrey
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
internal fun HomeScreen(viewModel: ConversionListViewModel) {
    val uiState: ConversionListViewModel.UiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            ConversionListViewModel.UiState.Error -> DataNotFoundAnim()
            ConversionListViewModel.UiState.Loading -> ProgressIndicator()
            is ConversionListViewModel.UiState.Content -> ConversionList(
                it.conversionRates,
                viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ConversionList(
    conversionRates: List<ConversionRatesOutput>,
    viewModel: ConversionListViewModel? = null
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    ModalBottomSheetLayout(
        sheetState = sheetState, sheetContent = {
            viewModel?.let { BasecodeList(sheetState, it) }
        }, modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                ConversionAppBar(sheetState, viewModel)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    Modifier.fillMaxWidth()
                ) {
                    items(items = conversionRates, key = { it.currencyCode }) { currency ->
                        CurrencyListItem(
                            currency.baseCode,
                            currency.currencyCode,
                            currency.rate.toString(),
                            "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png",
                            viewModel?.amount ?: 1.0
                        )
                    }
                }

            }
        }
    }

}


